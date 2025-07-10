class AnalyticsChartFragment : Fragment() {

    private lateinit var barChart: BarChart
    private val dhis2BaseUrl = "http://pfizer.dhis.et/"
    private val username = "YOUR_USERNAME"
    private val password = "YOUR_PASSWORD"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_analytics_chart, container, false)
        barChart = view.findViewById(R.id.barChart)

        fetchAnalyticsData()

        return view
    }

    private fun fetchAnalyticsData() {
        val client = OkHttpClient()
        val credentials = Credentials.basic(username, password)
        val url = "$dhis2BaseUrl/api/analytics.json?dimension=dx:ZVEdYzo7ZQy.REPORTING_RATE;ZVEdYzo7ZQy.REPORTING_RATE_ON_TIME;ZVEdYzo7ZQy.EXPECTED_REPORTS&dimension=pe:LAST_12_MONTHS&filter=ou:cetvP5BVp3S"

        val request = Request.Builder()
            .url(url)
            .header("Authorization", credentials)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("Analytics", "API Error", e)
            }

            override fun onResponse(call: Call, response: Response) {
                val json = JSONObject(response.body?.string() ?: return)
                val rows = json.getJSONArray("rows")

                val valuesMap = mutableMapOf<String, MutableMap<String, Float>>() // [period][indicator]

                for (i in 0 until rows.length()) {
                    val row = rows.getJSONArray(i)
                    val indicator = row.getString(0)
                    val period = row.getString(1)
                    val value = row.getDouble(2).toFloat()

                    valuesMap.getOrPut(period) { mutableMapOf() }[indicator] = value
                }

                // Show on UI thread
                activity?.runOnUiThread {
                    renderChart(valuesMap)
                }
            }
        })
    }

    private fun renderChart(data: Map<String, Map<String, Float>>) {
        val indicators = listOf(
            "ZVEdYzo7ZQy.REPORTING_RATE",
            "ZVEdYzo7ZQy.REPORTING_RATE_ON_TIME",
            "ZVEdYzo7ZQy.EXPECTED_REPORTS"
        )

        val periods = data.keys.sorted()

        val entriesByIndicator = indicators.map { indicatorId ->
            val entries = periods.mapIndexed { index, period ->
                val value = data[period]?.get(indicatorId) ?: 0f
                BarEntry(index.toFloat(), value)
            }
            BarDataSet(entries, indicatorId).apply {
                color = when (indicatorId) {
                    "ZVEdYzo7ZQy.REPORTING_RATE" -> Color.BLUE
                    "ZVEdYzo7ZQy.REPORTING_RATE_ON_TIME" -> Color.GREEN
                    else -> Color.RED
                }
            }
        }

        val barData = BarData(entriesByIndicator)
        barChart.data = barData

        barChart.xAxis.valueFormatter = IndexAxisValueFormatter(periods)
        barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        barChart.description.isEnabled = false
        barChart.invalidate()
    }
}
