package me.example.kotlinplayground.demo.presentation

import org.xml.sax.InputSource
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.StringReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import javax.xml.parsers.DocumentBuilderFactory
import org.w3c.dom.Element

fun main() {
    val serviceKey =
        "Pke8zh3ass2GAdZYmNXww1%2ByIp5%2FHgtUUbuL1pNB6jU%2FOvyC0bsazMrIZFZqE0PMO7gFMXCxgFIt5bkrkzX7EA%3D%3D"
    val solYear = "2026"
    val solMonth = "06"

    val urlBuilder =
        StringBuilder("http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo")
            .append("?${URLEncoder.encode("serviceKey", "UTF-8")}=$serviceKey")
            .append(
                "&${URLEncoder.encode("solYear", "UTF-8")}=${
                    URLEncoder.encode(
                        solYear,
                        "UTF-8"
                    )
                }"
            )
            .append(
                "&${URLEncoder.encode("solMonth", "UTF-8")}=${
                    URLEncoder.encode(
                        solMonth,
                        "UTF-8"
                    )
                }"
            )

    val url = URL(urlBuilder.toString())
    val conn = url.openConnection() as HttpURLConnection

    conn.requestMethod = "GET"
    conn.setRequestProperty("Content-type", "application/json")

    println("Response code: ${conn.responseCode}")

    val reader = if (conn.responseCode in 200..299) {
        BufferedReader(InputStreamReader(conn.inputStream))
    } else {
        BufferedReader(InputStreamReader(conn.errorStream))
    }

    val response = reader.useLines { lines ->
        lines.joinToString("")
    }

    prettyPrintXmlResponse(response);
    conn.disconnect()
}

fun prettyPrintXmlResponse(xml: String) {
    val dbFactory = DocumentBuilderFactory.newInstance()
    val dBuilder = dbFactory.newDocumentBuilder()
    val doc = dBuilder.parse(InputSource(StringReader(xml)))

    val items = doc.getElementsByTagName("item")
    for (i in 0 until items.length) {
        val item = items.item(i) as Element
        val dateName = item.getElementsByTagName("dateName").item(0).textContent
        val locdate = item.getElementsByTagName("locdate").item(0).textContent
        val isHoliday = item.getElementsByTagName("isHoliday").item(0).textContent

        println("📅 날짜: $locdate")
        println("🎉 이름: $dateName")
        println("🚦 공휴일 여부: $isHoliday")
        println("-----------")
    }
}

