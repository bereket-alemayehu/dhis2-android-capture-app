
/*
class AnalyticsChartFragment : Fragment() {

    private val dhis2BaseUrl = "http://pfizer.dhis.et/"
    private val username = "YOUR_USERNAME"
    private val password = "YOUR_PASSWORD"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

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
             
            }
        })
    }

   
}
*/