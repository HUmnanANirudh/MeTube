import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.first.Purple
import com.example.first.R

@Composable
fun AppIcon(modifier: Modifier){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "App Icon",
            modifier =  Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "MeTube",
            style = TextStyle(fontSize = 40.sp, fontWeight = FontWeight.Bold, color = Purple)
        )
    }
}