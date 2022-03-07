package com.faris.runningapp.other

import android.annotation.SuppressLint
import android.content.Context
import android.widget.TextView
import com.faris.runningapp.R
import com.faris.runningapp.databinding.MarkerViewBinding
import com.faris.runningapp.db.Run
import com.faris.runningapp.util.TrackingUtility
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("ViewConstructor")
class CustomMarkerView(
    private val runs: List<Run>,
    c: Context,
    layoutId: Int
) : MarkerView(c, layoutId) {

    override fun getOffset(): MPPointF {
        return MPPointF(-width / 2f, -height.toFloat())
    }

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        super.refreshContent(e, highlight)
        if (e == null) {
            return
        }
        val curRunPos = e.x.toInt()
        val run = runs[curRunPos]
        val cal = Calendar.getInstance().apply {
            timeInMillis = run.timestamp
        }
        val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
        findViewById<TextView>(R.id.tvDate).text = dateFormat.format(cal.time)

        val avgSpeed = "${run.avgSpeedInKph}km/h"
        findViewById<TextView>(R.id.tvAvgSpeed).text = avgSpeed

        val distanceInKm = "${run.distanceInMeters / 1000f}km"
        findViewById<TextView>(R.id.tvDistance).text = distanceInKm

        findViewById<TextView>(R.id.tvDuration).text =
            TrackingUtility.getFormattedStopWatch(run.timeInMillis)

        val caloriesBurned = "${run.caloriesBurned}kcal"
        findViewById<TextView>(R.id.tvCaloriesBurned).text = caloriesBurned

    }
}