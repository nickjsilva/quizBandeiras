package com.example.quizbandeiras;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;




public class Bandeira1 extends AppCompatActivity {

    public static final String EXTRA_NOME_USUARIO = "extra_nome_usuario";

    private String nomeUsuario;

    private MediaPlayer mediaPlayer;

    private List<Pergunta> perguntas;
    private int perguntaAtual = 0;
    private int pontuacao = 0;

    private ImageView imageView;
    private TextView textViewEnunciado;
    private RadioGroup radioGroup;
    private RadioButton alternativa1, alternativa2, alternativa3, alternativa4;
    private Button btnResponder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bandeira1);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Recebe o nome digitado na MainActivity
        nomeUsuario = getIntent().getStringExtra(EXTRA_NOME_USUARIO);
        if (nomeUsuario == null || nomeUsuario.trim().isEmpty()) {
            nomeUsuario = "Jogador";
        }

        // Vincula as views
        imageView = findViewById(R.id.imageView);
        textViewEnunciado = findViewById(R.id.textViewEnunciado);
        radioGroup = findViewById(R.id.radioGroup);
        alternativa1 = findViewById(R.id.alternativa1);
        alternativa2 = findViewById(R.id.alternativa2);
        alternativa3 = findViewById(R.id.alternativa3);
        alternativa4 = findViewById(R.id.alternativa4);
        btnResponder = findViewById(R.id.btnResponder);

        // Reinicia contadores (importante quando "RESPONDER NOVAMENTE" reabre esta Activity)
        perguntaAtual = 0;
        pontuacao = 0;

        // Monta a lista com as 10 perguntas
        montarPerguntas();

        // Exibe a primeira pergunta
        exibirPergunta();

        btnResponder.setOnClickListener(v -> verificarResposta());
    }

    private void montarPerguntas() {
        perguntas = new ArrayList<>();

        perguntas.add(new Pergunta(
                R.drawable.bandeira_quirguistao,
                "Que país é este?",
                Arrays.asList("Quirguistão", "Cazaquistão", "Uzbequistão", "Turcomenistão"),
                0
        ));

        perguntas.add(new Pergunta(
                R.drawable.bandeira_mongolia,
                "Que país é este?",
                Arrays.asList("Nepal", "Cazaquistão", "Mongólia", "Bangladesh"),
                2
        ));

        perguntas.add(new Pergunta(
                R.drawable.bandeira_mianmar,
                "Que país é este?",
                Arrays.asList("Etiópia", "Mianmar", "Guiana", "Suriname"),
                1
        ));

        perguntas.add(new Pergunta(
                R.drawable.bandeira_tadjiquistao,
                "Que país é este?",
                Arrays.asList("Hungria", "Bulgária", "Tadjiquistão", "Kuwait"),
                2
        ));

        perguntas.add(new Pergunta(
                R.drawable.bandeira_sri_lanka,
                "Que país é este?",
                Arrays.asList("Butão", "Zâmbia", "Sri Lanka", "Nepal"),
                2
        ));

        perguntas.add(new Pergunta(
                R.drawable.bandeira_camboja,
                "Que país é este?",
                Arrays.asList("Tailândia", "Laos", "Vietnã", "Camboja"),
                3
        ));

        perguntas.add(new Pergunta(
                R.drawable.bandeira_butao,
                "Que país é este?",
                Arrays.asList("China", "Butão", "Sri Lanka", "Nepal"),
                1
        ));

        perguntas.add(new Pergunta(
                R.drawable.bandeira_turcomenistao,
                "Que país é este?",
                Arrays.asList("Arábia Saudita", "Líbia", "Turcomenistão", "Argélia"),
                2
        ));

        perguntas.add(new Pergunta(
                R.drawable.bandeira_laos,
                "Que país é este?",
                Arrays.asList("Coreia do Norte", "Camboja", "Tailândia", "Laos"),
                3
        ));

        // Questão 10 - única que toca a música
        perguntas.add(new Pergunta(
                R.drawable.bandeira_iemen,
                "Que país é este?",
                Arrays.asList("Maior Time do Brasil", "Egito", "Somália", "Iêmen"),
                3
        ));
    }

    private void exibirPergunta() {
        Pergunta pergunta = perguntas.get(perguntaAtual);

        imageView.setImageResource(pergunta.getImagemBandeira());
        textViewEnunciado.setText(pergunta.getEnunciado());

        List<String> alternativas = pergunta.getAlternativas();
        alternativa1.setText(alternativas.get(0));
        alternativa2.setText(alternativas.get(1));
        alternativa3.setText(alternativas.get(2));
        alternativa4.setText(alternativas.get(3));

        radioGroup.clearCheck();

        // Só toca a música quando for a última pergunta (Iêmen)
        boolean ehPerguntaDoIemen = (perguntaAtual == perguntas.size() - 1);

        if (ehPerguntaDoIemen) {
            iniciarMusica();
        } else {
            pararMusica();
        }
    }

    private void iniciarMusica() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.hinospfc);
            if (mediaPlayer != null) {
                mediaPlayer.setLooping(true);
            }
        }
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    private void pararMusica() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private int getIndiceSelecionado(int selecionadoId) {
        if (selecionadoId == R.id.alternativa1) return 0;
        if (selecionadoId == R.id.alternativa2) return 1;
        if (selecionadoId == R.id.alternativa3) return 2;
        if (selecionadoId == R.id.alternativa4) return 3;
        return -1;
    }

    private void verificarResposta() {
        int selecionadoId = radioGroup.getCheckedRadioButtonId();

        if (selecionadoId == -1) {
            Toast.makeText(this, "Selecione uma alternativa!", Toast.LENGTH_SHORT).show();
            return;
        }

        int indiceSelecionado = getIndiceSelecionado(selecionadoId);
        Pergunta pergunta = perguntas.get(perguntaAtual);

        if (indiceSelecionado == pergunta.getIndiceCorreto()) {
            pontuacao++;
        }

        perguntaAtual++;

        if (perguntaAtual < perguntas.size()) {
            exibirPergunta();
        } else {
            finalizarQuiz();
        }
    }
    private void finalizarQuiz() {
        pararMusica();

        Intent intent = new Intent(Bandeira1.this, RankingActivity.class);
        intent.putExtra(RankingActivity.EXTRA_NOME, nomeUsuario);
        intent.putExtra(RankingActivity.EXTRA_PONTUACAO, pontuacao);
        intent.putExtra(RankingActivity.EXTRA_TOTAL_PERGUNTAS, perguntas.size());
        startActivity(intent);
        finish();
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
        boolean ehPerguntaDoIemen = perguntas != null
                && perguntaAtual == perguntas.size() - 1;
        if (ehPerguntaDoIemen && mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pararMusica();
    }
}