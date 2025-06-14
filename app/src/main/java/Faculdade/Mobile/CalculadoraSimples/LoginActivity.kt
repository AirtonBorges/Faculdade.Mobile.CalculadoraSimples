package Faculdade.Mobile.CalculadoraSimples

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val cpfEditor = findViewById<EditText>(R.id.cpf)
        val senhaEditor = findViewById<EditText>(R.id.senha);
        val botao = findViewById<Button>(R.id.button);

        monitorarCamposParaHabilitarBotao(cpfEditor, senhaEditor, botao);

        val cpfCorreto = "12345678900"
        val senhaCorreta = "1234"

        botao.setOnClickListener {
            val cpf = cpfEditor.text.toString()
            val senha = senhaEditor.text.toString()

            if (cpf == cpfCorreto && senha == senhaCorreta) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                AlertDialog.Builder(this)
                    .setTitle("Erro de login")
                    .setMessage("CPF ou senha inválidos.")
                    .setPositiveButton("OK", null)
                    .show()
            }
        }
    }

    fun monitorarCamposParaHabilitarBotao(campo1: EditText, campo2: EditText, botao: View) {
        botao.isEnabled = false;

        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val preenchido = campo1.text.isNotBlank() && campo2.text.isNotBlank()
                botao.isEnabled = preenchido;
            }
            override fun afterTextChanged(s: Editable?) {}
        }

        campo1.addTextChangedListener(watcher)
        campo2.addTextChangedListener(watcher)
    }
}