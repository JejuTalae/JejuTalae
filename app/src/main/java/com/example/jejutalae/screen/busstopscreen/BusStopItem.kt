
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.jejutalae.data.BusStop


@Composable
fun BusStopList(busStops: List<BusStop>) {
    Column(
        modifier = Modifier
            .padding(start = 30.dp)
            .verticalScroll(rememberScrollState())
    ) {
        busStops.forEachIndexed { index, busStop ->
            BusStopItem(
                busStop = busStop,
                isFirst = (index == 0),
                isLast = (index == busStops.size - 1)
            )
        }
    }
}

@Composable
fun BusStopItem(busStop: BusStop, isFirst: Boolean, isLast: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color(0xFFFFFFFF))
            .padding(start = 10.dp)
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            // 세로 선
            if (!isFirst) {
                Box(
                    modifier = Modifier
                        .width(8.dp)
                        .height(31.dp)
                        .offset(x = 0.dp, y = (-15).dp)
                        .background(Color(0xff41C3E7))
                        .zIndex(-1f) // 선을 뒤로 보냄
                )
            }
            // 정류장 아이콘 (원)
            Box(
                modifier = Modifier
                    .size(15.dp)
                    .background(Color(0xff41C3E7), CircleShape)
            )
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(Color(0xFFFFFFFF), CircleShape)
            )
            // 마지막 정류소가 아니면 하단 선 추가
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(8.dp)
                        .height(31.dp)
                        .offset(x = 0.dp, y = 15.dp)
                        .background(Color(0xff41C3E7))
                        .zIndex(-1f) // 선을 뒤로 보냄
                )
            }
        }

        Spacer(modifier = Modifier.width(20.dp))

        // 정류장 이름과 위치 정보
        Column {
            Text(text = busStop.name)
            Text(text = busStop.location, color = Color.Gray)
        }
    }
}