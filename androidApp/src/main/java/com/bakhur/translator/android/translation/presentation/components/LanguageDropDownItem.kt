package com.bakhur.translator.android.translation.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bakhur.translator.core.presentation.UiLanguage

@Composable
fun LanguageDropDownItem(
    modifier: Modifier = Modifier,
    language: UiLanguage,
    onClick: () -> Unit
) {
    DropdownMenuItem(
        modifier = modifier,
        text = {
            Text(
                text = language.language.langName
            )
        },
        leadingIcon = {
            AsyncImage(
                modifier = Modifier.size(40.dp),
                model = language.drawableRes,
                contentDescription = language.language.langName
            )
            Spacer(modifier = Modifier.width(16.dp))
        },
        onClick = onClick
    )
}