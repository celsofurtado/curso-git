package br.senai.sp.catlogodefilmes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.senai.sp.dao.FilmeDAO;
import br.senai.sp.modelo.Filme;

public class CadastroFilmeActivity extends AppCompatActivity {

    private CadastroFilmeHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_filme);

        helper = new CadastroFilmeHelper(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_cadastro_filmes, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_salvar:

                Filme filme = helper.getFilme();

                FilmeDAO dao = new FilmeDAO(this);
                dao.gravar(filme);
                dao.close();
                Toast.makeText(this, filme.getTitulo() + " gravado com sucesso", Toast.LENGTH_LONG).show();
                finish();

                break;
            case R.id.menu_del:
                Toast.makeText(CadastroFilmeActivity.this,
                        "Excluir", Toast.LENGTH_LONG)
                        .show();
                break;
            case R.id.menu_configuracoes:
                Toast.makeText(CadastroFilmeActivity.this,
                        "Configurações", Toast.LENGTH_LONG)
                        .show();
                break;
            default:
                Toast.makeText(CadastroFilmeActivity.this,
                         "Nada", Toast.LENGTH_LONG)
                         .show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
