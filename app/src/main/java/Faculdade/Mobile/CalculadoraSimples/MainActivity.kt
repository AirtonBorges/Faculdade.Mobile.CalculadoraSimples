package Faculdade.Mobile.CalculadoraSimples

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    @SuppressLint("CutPasteId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var primeiroValor = 0f;
        val primeiroValorEditor = findViewById<EditText>(R.id.primeiroNumero)

        primeiroValorEditor.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(valor: CharSequence?, start: Int, before: Int, count: Int) {
                primeiroValor = valor.toString().toFloatOrNull() ?: 0f
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        var segundoValor = 0f;
        val segundoValorEditor = findViewById<EditText>(R.id.segundoNumero)
        segundoValorEditor.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(valor: CharSequence?, start: Int, before: Int, count: Int) {
                segundoValor = valor.toString().toFloatOrNull() ?: 0f
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        val botoes = mutableListOf<Button>();
        val botaoAdicao = findViewById<Button>(R.id.adicao);
        val botaoSubtracao = findViewById<Button>(R.id.subtracao);
        val botaoDivisao = findViewById<Button>(R.id.divisao);
        val botaoMultiplicacao = findViewById<Button>(R.id.multiplicacao);
        val botaoCompartilhar = findViewById<Button>(R.id.compartilhar);

        botoes.add(botaoAdicao);
        botoes.add(botaoSubtracao);
        botoes.add(botaoDivisao);
        botoes.add(botaoMultiplicacao);

        monitorarCamposParaHabilitarBotao(primeiroValorEditor, segundoValorEditor, botoes);

        val resultadoView = findViewById<android.widget.TextView>(R.id.resultado)
        var resultado = 0f;
        botaoAdicao.setOnClickListener {
            resultado = primeiroValor + segundoValor
            resultadoView.text = "Resultado: $resultado"
        }

        botaoSubtracao.setOnClickListener {
            resultado = primeiroValor - segundoValor
            resultadoView.text = "Resultado: $resultado"
        }

        botaoMultiplicacao.setOnClickListener {
            resultado = primeiroValor * segundoValor
            resultadoView.text = "Resultado: $resultado"
        }

        botaoDivisao.setOnClickListener {
            resultado = if (segundoValor != 0f) {
                primeiroValor / segundoValor
            } else {
                resultadoView.text = "Divisão por zero"
                return@setOnClickListener
            }
            resultadoView.text = "Resultado: $resultado"
        }

        botaoCompartilhar.setOnClickListener {
            val mensagem = "Oi! Você sabia que $primeiroValor mais $segundoValor é igual a $resultado?"
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, mensagem)
            }
            startActivity(Intent.createChooser(intent, "Compartilhar via"))
        }
    }

    fun monitorarCamposParaHabilitarBotao(campo1: EditText, campo2: EditText, botao: List<View>) {
        botao.forEach { it.isEnabled = false }

        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val preenchido = campo1.text.isNotBlank() && campo2.text.isNotBlank()
                botao.forEach { it.isEnabled = preenchido }
            }
            override fun afterTextChanged(s: Editable?) {}
        }

        campo1.addTextChangedListener(watcher)
        campo2.addTextChangedListener(watcher)
    }
}