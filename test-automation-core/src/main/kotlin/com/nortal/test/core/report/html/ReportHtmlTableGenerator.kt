package com.nortal.test.core.report.html

import com.google.common.collect.ImmutableList
import com.nortal.test.core.file.ClasspathFileResolver
import org.springframework.stereotype.Component
import java.lang.StringBuilder
import java.util.function.Consumer

@Component
class ReportHtmlTableGenerator(
    private val classpathFileResolver: ClasspathFileResolver
) {
    fun generateTable(table: List<List<String?>>, appendRowNums: Boolean): String {
        val html = StringBuilder("<table class=\"table table-striped table-fit-content\">")
        populateHeader(html, table[0], appendRowNums)
        populateBody(html, table, appendRowNums)
        html.append("</table>")
        html.append(getTableCss())
        return html.toString()
    }

    private fun populateHeader(html: StringBuilder, headerValues: List<String?>, appendRowNums: Boolean) {
        val finalHeaderValues: List<String?>
        finalHeaderValues = if (appendRowNums) {
            ImmutableList.Builder<String?>().addAll(headerValues).add("#").build()
        } else {
            headerValues
        }
        html.append("<thead><tr>")
        finalHeaderValues.forEach(Consumer { value: String? -> html.append("<th>").append(value).append("</th>") })
        html.append("</tr></thead>")
    }

    //TODO optimize, quick hack
    private fun populateBody(html: StringBuilder, table: List<List<String?>>, appendRowNums: Boolean) {
        html.append("<tbody>")
        for (i in 1 until table.size) {
            val rowValues = table[i]
            var rowCss = ""
            if (rowValues.stream().anyMatch { anObject: String? -> "OK".equals(anObject) }) {
                rowCss = "success"
            } else if (rowValues.stream().anyMatch { anObject: String? -> "FAILED".equals(anObject) }) {
                rowCss = "danger"
            } else if (rowValues.stream().anyMatch { anObject: String? -> "SKIPPED".equals(anObject) }) {
                rowCss = "warning"
            }
            html.append("<tr class=\"").append(rowCss).append("\">")
            if (appendRowNums) {
                html.append("<td>").append(i).append("</td>")
            }
            rowValues.forEach(Consumer { value: String? -> html.append("<td>").append(value).append("</td>") })
            html.append("</tr>")
        }
        html.append("</tbody>")
    }

    private fun getTableCss(): String {
        val css = classpathFileResolver.getFileAsString("report/css/table.css")
        return """
            <style>
            $css
            </style>
                """.trimIndent()
    }
}