package com.bakhur.translator.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bakhur.translator.android.R
import com.bakhur.translator.core.presentation.UiLanguage
import com.bakhur.translator.presentation.theme.LightBlue

@Composable
fun LanguageDropDown(
    modifier: Modifier = Modifier,
    language: UiLanguage,
    isOpen: Boolean,
    onClick: () -> Unit,
    onDismiss: () -> Unit,
    onSelectLanguage: (UiLanguage) -> Unit
) {
    Box(modifier = modifier) {
        DropdownMenu(
            expanded = isOpen,
            onDismissRequest = onDismiss
        ) {
            UiLanguage.allLanguages.forEach { language ->
                LanguageDropDownItem(
                    modifier = Modifier.fillMaxWidth(),
                    language = language,
                    onClick = { onSelectLanguage(language) }
                )
            }
        }

        Row(
            modifier = Modifier
                .clickable(onClick = onClick)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier.size(30.dp),
                model = language.drawableRes,
                contentDescription = language.language.langName
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = language.language.langName,
                color = LightBlue
            )

            Icon(
                modifier = Modifier.size(30.dp),
                imageVector = if (isOpen) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                contentDescription = if (isOpen) {
                    stringResource(id = R.string.close)
                } else {
                    stringResource(id = R.string.open)
                },
                tint = LightBlue
            )
        }
    }
}