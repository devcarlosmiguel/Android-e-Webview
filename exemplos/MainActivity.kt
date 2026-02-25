package com.cloudforge.calculadoram2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true

        // Adicionar interface JavaScript para compartilhar
        webView.addJavascriptInterface(WebAppInterface(this), "AndroidShare")

        webView.webViewClient = WebViewClient()
        webView.loadUrl("file:///android_asset/index.html")
    }

    // Classe interna para interface JavaScript
    inner class WebAppInterface(private val context: MainActivity) {

        @JavascriptInterface
        fun share(text: String) {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, text)
            }
            context.startActivity(Intent.createChooser(shareIntent, "Compartilhar orçamento"))
        }
    }

    override fun onBackPressed() {
        // Mostrar diálogo de confirmação com ícone
        AlertDialog.Builder(this)
            .setTitle("Sair do aplicativo?")
            .setMessage("Gostou do app? Deixe sua avaliação!")
            .setIcon(R.mipmap.ic_launcher) // Adiciona o ícone do app
            .setPositiveButton("Avaliar") { _, _ ->
                // Abrir Play Store
                try {
                    val appPackageName = packageName
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName"))
                    startActivity(intent)
                } catch (e: Exception) {
                    // Se Play Store não estiver instalada, abrir no navegador
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName"))
                    startActivity(intent)
                }
                // Fechar app após abrir Play Store
                finish()
            }
            .setNegativeButton("Cancelar", null) // Apenas fecha o diálogo
            .setNeutralButton("Sair") { _, _ ->
                // Sair do app
                finish()
            }
            .setCancelable(true)
            .show()
    }

}
