package br.senai.sp.catlogodefilmes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.senai.sp.dao.FilmeDAO;
import br.senai.sp.modelo.Filme;

public class MainActivity extends AppCompatActivity {

    private ListView listaFilmes;
    private Button btnNovoFilme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNovoFilme = findViewById(R.id.bt_novo_filme);

        // *** Associo o objeto ListView do Java à View ListView do layout xml
        listaFilmes = findViewById(R.id.list_filmes);

        // *** Ação do botão novo
        btnNovoFilme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cadastroFilme = new Intent(MainActivity.this, CadastroFilmeActivity.class);
                startActivity(cadastroFilme);
            }
        });

        listaFilmes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                Filme filme = (Filme) lista.getItemAtPosition(position);
                Toast.makeText(MainActivity.this, filme.getTitulo(), Toast.LENGTH_LONG).show();
            }
        });

        // *** CRIAÇÃO DE UM MENU DE CONTEXTO PARA A LISTA DE FILMES
        registerForContextMenu(listaFilmes);

    }

    @Override
    protected void onResume() {
        carregarLista();
        super.onResume();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {

        MenuItem deletar = menu.add("Deletar");

        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Filme filme = (Filme) listaFilmes.getItemAtPosition(info.position);

                FilmeDAO dao = new FilmeDAO(MainActivity.this);
                dao.excluir(filme);
                dao.close();

                carregarLista();

                Toast.makeText(MainActivity.this, "Filme excluído!", Toast.LENGTH_LONG).show();
                return false;
            }
        });


    }


    private void carregarLista(){
        // *** MATRIZ DE FILMES QUE SERÃO EXIBIDOS NO ListView
        FilmeDAO dao = new FilmeDAO(this);
        List<Filme> filmes = dao.getFilmes();
        dao.close();


        // *** Definimos um adapter para carregar os dados da matriz na ListView
        // *** utilizando um layout pronto (simple_list_item_1)
        ArrayAdapter<Filme> listaFilmesAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filmes);

        // *** Injetamos o adapter no objeto ListView
        listaFilmes.setAdapter(listaFilmesAdapter);

    }

}
