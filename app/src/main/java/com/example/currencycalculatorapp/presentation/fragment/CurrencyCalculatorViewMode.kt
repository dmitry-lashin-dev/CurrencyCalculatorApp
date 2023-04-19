package com.example.currencycalculatorapp.presentation.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.currencycalculatorapp.common.SingleLiveEvent
import com.example.currencycalculatorapp.common.toPrecision
import com.example.currencycalculatorapp.domain.GetAllRatesUseCase
import com.example.currencycalculatorapp.domain.SaveExchangeHistoryUseCase
import com.example.currencycalculatorapp.domain.models.presentation.CalendarDataUi
import com.example.currencycalculatorapp.domain.models.presentation.CurrencyDataModel
import com.example.currencycalculatorapp.domain.models.presentation.CurrencyDataUi
import com.example.currencycalculatorapp.domain.models.presentation.ExchangeTransitionModel
import com.example.currencycalculatorapp.presentation.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Calendar

class CurrencyCalculatorViewMode(
    private val getAllRatesUseCase: GetAllRatesUseCase = GetAllRatesUseCase(),
    private val saveExchangeHistoryUseCase: SaveExchangeHistoryUseCase = SaveExchangeHistoryUseCase()
) : BaseViewModel() {

    companion object {
        private const val DEFAULT_RATE = 0.0
        private const val DEBOUNCE_TIME = 300L
        private const val PRECISION = 2
    }

    private val ratesModelFlow = MutableStateFlow<CurrencyDataModel?>(null)
    private val calendarDataFlow = MutableSharedFlow<CalendarDataUi?>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    private val selectedDateFlow = MutableStateFlow<String?>(null)
    private val showSelectCurrencyActionFlow = MutableSharedFlow<Boolean?>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    private val selectedOriginalCurrencyFlow = MutableStateFlow<CurrencyDataUi?>(null)
    private val selectedTargetCurrencyFlow = MutableStateFlow<CurrencyDataUi?>(null)
    private val amountFlow = MutableStateFlow<String?>(null)

    val calendarDataEvent = SingleLiveEvent<CalendarDataUi>()
    val selectedDateLd = selectedDateFlow.filterNotNull().asLiveData()
    val showSelectCurrencyDialogEvent = SingleLiveEvent<Pair<Boolean, List<CurrencyDataUi>>>()
    val selectedOriginalLd = selectedOriginalCurrencyFlow.filterNotNull()
        .map {
            it.name
        }
        .asLiveData()
    val selectedTargetLd = selectedTargetCurrencyFlow.filterNotNull()
        .map {
            it.name
        }
        .asLiveData()
    val exchangeStateLd = combine(
        selectedDateFlow,
        selectedOriginalCurrencyFlow,
        selectedTargetCurrencyFlow,
        amountFlow
    ) { date, originCurrency, targetCurrency, amount ->
        !(date.isNullOrEmpty() || originCurrency == null || targetCurrency == null || amount.isNullOrEmpty())
    }
        .asLiveData()
    val savedAction = MutableLiveData<Unit>()

    @OptIn(FlowPreview::class)
    val willReceiveLd = ratesModelFlow.filterNotNull()
        .combine(selectedOriginalCurrencyFlow.filterNotNull()) { ratesModel, uiModel ->
            val rate = ratesModel.currenciesRatesData[uiModel.ticker] ?: DEFAULT_RATE
            ExchangeTransitionModel(ratesModel, rate)
        }
        .combine(selectedTargetCurrencyFlow.filterNotNull()) { transitionModel, uiModel ->
            val targetRate =
                transitionModel.ratesModel.currenciesRatesData[uiModel.ticker] ?: DEFAULT_RATE
            transitionModel.apply {
                this.targetRate = targetRate
                this.targetTicker = uiModel.ticker
            }
            transitionModel
        }.combine(amountFlow
            .filterNotNull()
            .debounce { if (it.isEmpty()) 0 else DEBOUNCE_TIME }) { model, amount ->
            val originInUah = model.originRate * (amount.toDoubleOrNull() ?: DEFAULT_RATE)
            val targetAmount = (originInUah / model.targetRate).toString().toPrecision(PRECISION)
            "$targetAmount ${model.targetTicker}"
        }
        .flowOn(Dispatchers.IO)
        .asLiveData()
    val historyNavigationLd = SingleLiveEvent<Unit>()

    init {
        getAllRates()
        viewModelScope.launch(Dispatchers.IO) {
            calendarDataFlow
                .filterNotNull()
                .collectLatest {
                    calendarDataEvent.postValue(it)
                }
        }
        viewModelScope.launch(Dispatchers.IO) {
            combine(
                showSelectCurrencyActionFlow.filterNotNull(),
                ratesModelFlow.filterNotNull()
            ) { isOriginal, model ->
                isOriginal to model.currenciesNamesList
            }
                .distinctUntilChanged()
                .collectLatest { pair ->
                    showSelectCurrencyDialogEvent.postValue(pair)
                }
        }
    }

    private fun getAllRates(date: String? = null) {
        showProgress()
        getAllRatesUseCase.apply {
            this.date = date
            execute {
                onComplete {
                    hideProgress()
                    ratesModelFlow.tryEmit(it)
                }
                onError {
                    hideProgress()
                }
            }
        }
    }

    fun processClickOnDateContainer() {
        val calendar = Calendar.getInstance()
        val model = CalendarDataUi(
            currentYear = calendar.get(Calendar.YEAR),
            currentMonth = calendar.get(Calendar.MONTH),
            currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        )
        calendarDataFlow.tryEmit(model)
    }

    fun processSelectedDate(year: Int, month: Int, day: Int) {
        val finalMonth = month + 1
        val monthTxt = if (finalMonth < 10) "0$finalMonth" else "$finalMonth"
        val requestDateString = "$year$monthTxt$day"
        val uiDate = "$day - $monthTxt - $year"
        selectedDateFlow.tryEmit(uiDate)
        getAllRates(requestDateString)
    }

    fun processClickOnCurrencyContainer(isOriginal: Boolean) {
        showSelectCurrencyActionFlow.tryEmit(isOriginal)
    }

    fun processSelectedCurrency(isOriginal: Boolean, model: CurrencyDataUi) {
        if (isOriginal) {
            selectedOriginalCurrencyFlow.tryEmit(model)
        } else {
            selectedTargetCurrencyFlow.tryEmit(model)
        }
    }

    fun processChangeAmount(amount: String) {
        amountFlow.tryEmit(amount)
    }

    fun processClickExchange() {
        val originTicker = selectedOriginalCurrencyFlow.value?.ticker ?: ""
        val amount = amountFlow.value ?: "0"
        val originAmountWithTicker = "$amount $originTicker"
        val targetAmount = willReceiveLd.value ?: "0"
        showProgress()
        saveExchangeHistoryUseCase.apply {
            this.originAmountWithTicker = originAmountWithTicker
            this.targetAmountWithTicker = targetAmount
            execute {
                onComplete {
                    hideProgress()
                    savedAction.postValue(Unit)
                }
            }
        }
    }

    fun processClickHistory() {
        historyNavigationLd.callSafe()
    }
}