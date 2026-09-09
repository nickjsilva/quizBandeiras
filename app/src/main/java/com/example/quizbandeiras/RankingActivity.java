package com.example.quizbandeiras;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RankingActivity extends AppCompatActivity {

    public static final String EXTRA_NOME = "extra_nome";
    public static final String EXTRA_PONTUACAO = "extra_pontuacao";
    public static final String EXTRA_TOTAL_PERGUNTAS = "extra_total_perguntas";

    private TextView textViewNome;
    private TextView textViewPontuacao;
    private Button btnResponderNovamente;
    private Button btnTelaInicial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ranking);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textViewNome = findViewById(R.id.textViewNome);
        textViewPontuacao = findViewById(R.id.textViewPontuacao);
        btnResponderNovamente = findViewById(R.id.btnResponderNovamente);
        btnTelaInicial = findViewById(R.id.btnTelaInicial);

        // Recebe os dados enviados pela tela do quiz
        String nome = getIntent().getStringExtra(EXTRA_NOME);
        int pontuacao = getIntent().getIntExtra(EXTRA_PONTUACAO, 0);
        int totalPerguntas = getIntent().getIntExtra(EXTRA_TOTAL_PERGUNTAS, 10);

        if (nome == null || nome.trim().isEmpty()) {
            nome = "Jogador";
        }

        textViewNome.setText("Nome: " + nome);
        textViewPontuacao.setText("Pontuação: " + pontuacao + " / " + totalPerguntas);

        btnResponderNovamente.setOnClickListener(v -> {
            Intent intent = new Intent(RankingActivity.this, Bandeira1.class);
            intent.putExtra(Bandeira1.EXTRA_NOME_USUARIO, getIntent().getStringExtra(EXTRA_NOME));
            // Limpa a pilha de activities do quiz para começar do zero
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        btnTelaInicial.setOnClickListener(v -> {
            Intent intent = new Intent(RankingActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}