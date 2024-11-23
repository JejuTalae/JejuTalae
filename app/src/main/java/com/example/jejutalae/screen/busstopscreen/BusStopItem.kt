
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


@Preview(showBackground = false, widthDp = 380, heightDp = 700)
@Composable
fun BusStopItemPreview() {
    // 샘플 데이터 생성
    val busList = listOf(
        BusStop("1", "부영아파트", "제주시 노형동"),
        BusStop("2", "한라중학교", "제주시 노형동"),
        BusStop("3", "롯데마트", "제주시 노형동"),
        BusStop("4", "노형오거리", "제주시 노형동"),
        BusStop("5", "한라병원", "제주시 연동"),
        BusStop("6", "제원아파트", "제주시 연동"),
        BusStop("7", "은남동", "제주시 연동"),
        BusStop("8", "제주도청(신제주로터리)", "제주시 연동"),
        BusStop("9", "오라3동", "제주시 오라동"),
        BusStop("10", "제주버스터미널", "제주시 오라동"),
        BusStop("11", "남서광마을", "제주시 서광로"),
        BusStop("12", "제주시청", "제주시 이도이동"),
        BusStop("13", "제주여자중고등학교", "제주시 이도이동"),
        BusStop("14", "아라초등학교", "제주시 아라일동"),
        BusStop("15", "인다마을", "제주시 아라일동"),
        BusStop("16", "제주대학교병원", "제주시 아라일동"),
        BusStop("17", "제주대학교(영주고,국제대)", "제주시 아라일동")
    )
    Column(modifier = Modifier
        .padding(start = 30.dp)
        .verticalScroll(rememberScrollState())) {
        busList.forEachIndexed{ index, busStop ->
            BusStopItem(busStop = busStop, isFirst = (index == 0), isLast = (index == busList.size - 1))
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