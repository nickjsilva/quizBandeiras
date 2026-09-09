package com.example.quizbandeiras;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
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

    private static final int PONTUACAO_MINIMA_MISSION_PASSED = 7;

    private TextView textViewNome;
    private TextView textViewPontuacao;
    private ImageView imageViewMissionPassed;
    private Button btnResponderNovamente;
    private Button btnTelaInicial;
    private MediaPlayer mediaPlayer;

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
        imageViewMissionPassed = findViewById(R.id.imageViewMissionPassed);
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

        boolean passouNaMissao = pontuacao >= PONTUACAO_MINIMA_MISSION_PASSED;

        // Exibe a imagem "Mission Passed" apenas se os acertos forem >= 7
        if (passouNaMissao) {
            imageViewMissionPassed.setVisibility(ImageView.VISIBLE);
        } else {
            imageViewMissionPassed.setVisibility(ImageView.GONE);
        }

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

        // Só toca o som "gta" quando o usuário passou na missão (pontuação >= 7)
        if (passouNaMissao) {
            mediaPlayer = MediaPlayer.create(this, R.raw.gta);
            if (mediaPlayer != null) {
                mediaPlayer.setLooping(false);
                mediaPlayer.start();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}