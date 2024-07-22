package com.example.tammela.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tammela.data.model.Sensor
import com.example.tammela.data.model.ShoppingItem

@Composable
fun ShoppingItemCard(item: ShoppingItem, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.padding(10.dp),
        color = MaterialTheme.colorScheme.background,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 2.dp,
        shadowElevation = 10.dp
    ) {
        Column (modifier = Modifier.fillMaxWidth())
        {
            Row (
                modifier = Modifier.padding(10.dp)
            )
            {
                Row(
                    modifier =  Modifier.weight(1f),
                )
                {
                    Text(
                        fontWeight = FontWeight(700),
                        text = item.user
                    )
                     Text(
                         modifier = Modifier.padding(start = 8.dp),
                         text = item.createDate
                     )
                }
                IconButton(
                    modifier = Modifier.height(20.dp).width(40.dp),
                    //enabled = buttonData.enabled,
                    onClick = {},
                ) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Check the product"
                    )
                }
                IconButton(
                    modifier = Modifier.height(20.dp).width(40.dp),
                    //enabled = buttonData.enabled,
                    onClick = {},
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Check the product"
                    )
                }
                IconButton(
                    modifier = Modifier.height(20.dp).width(40.dp),
                    //enabled = buttonData.enabled,
                    onClick = {},
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Check the product"
                    )
                }
            }
            Row (
                modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
            )
            {
                Text(
                    modifier =  Modifier.weight(1f),
                    //fontWeight = FontWeight(700),
                    text = item.item
                )
            }
            Row (
                modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
            )
            {

            }
        }
    }
}
