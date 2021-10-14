package br.com.alura.estoque.ui.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import br.com.alura.estoque.R;
import br.com.alura.estoque.model.Produto;
import br.com.alura.estoque.repository.ProdutoRepository;
import br.com.alura.estoque.ui.dialog.EditaProdutoDialog;
import br.com.alura.estoque.ui.dialog.SalvaProdutoDialog;
import br.com.alura.estoque.ui.recyclerview.adapter.ListaProdutosAdapter;

public class ListaProdutosActivity extends AppCompatActivity {

    private static final String TITULO_APPBAR = "Lista de produtos";
    public static final String RESPOSTA_ERRO_CARREGAR_PRODUTOS = "Não foi possivel carregar os produtos novos";
    public static final String RESPOSTA_ERRO_REMOVER_PRODUTO = "Não foi possivel remover produto";
    public static final String RESPOSTA_ERRO_SALVAR = "Não foi possivel salvar o produto";
    public static final String RESPOSTA_ERRO_EDITAR = "Não foi possivel editar o produto";
    private ListaProdutosAdapter adapter;
    private ProdutoRepository produtoRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produtos);
        setTitle(TITULO_APPBAR);

        configuraListaProdutos();
        configuraFabSalvaProduto();

        produtoRepository = new ProdutoRepository(this);
        produtoRepository.buscaProdutos(new ProdutoRepository.DadosCarregadosCallback<List<Produto>>() {
            @Override
            public void quandoSucesso(List<Produto> resultado) {
                adapter.atualiza(resultado);
            }

            @Override
            public void quandoFalha(String erro) {
                Toast.makeText(ListaProdutosActivity.this, RESPOSTA_ERRO_CARREGAR_PRODUTOS, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void configuraListaProdutos() {
        RecyclerView listaProdutos = findViewById(R.id.activity_lista_produtos_lista);
        adapter = new ListaProdutosAdapter(this, this::abreFormularioEditaProduto);
        listaProdutos.setAdapter(adapter);
        adapter.setOnItemClickRemoveContextMenuListener((posicao, produto) -> produtoRepository.remove(produto,
                remover(posicao))
        );
    }

    @NonNull
    private ProdutoRepository.DadosCarregadosCallback<Void> remover(int posicao) {
        return new ProdutoRepository.DadosCarregadosCallback<Void>() {
            @Override
            public void quandoSucesso(Void resultado) {
                adapter.remove(posicao);
            }

            @Override
            public void quandoFalha(String erro) {
                Toast.makeText(ListaProdutosActivity.this, RESPOSTA_ERRO_REMOVER_PRODUTO, Toast.LENGTH_SHORT).show();
            }
        };
    }


    private void configuraFabSalvaProduto() {
        FloatingActionButton fabAdicionaProduto = findViewById(R.id.activity_lista_produtos_fab_adiciona_produto);
        fabAdicionaProduto.setOnClickListener(v -> abreFormularioSalvaProduto());
    }

    private void abreFormularioSalvaProduto() {
        new SalvaProdutoDialog(this, produto -> produtoRepository
                .salva(produto, salva(produto)))
                .mostra();
    }

    @NonNull
    private ProdutoRepository.DadosCarregadosCallback<Produto> salva(Produto produto) {
        return new ProdutoRepository.DadosCarregadosCallback<Produto>() {
            @Override
            public void quandoSucesso(Produto resultado) {
                adapter.adiciona(produto);
            }

            @Override
            public void quandoFalha(String erro) {
                Toast.makeText(ListaProdutosActivity.this, RESPOSTA_ERRO_SALVAR, Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void abreFormularioEditaProduto(int posicao, Produto produto) {
        new EditaProdutoDialog(this, produto,
                produtoCriado -> produtoRepository.edita(produtoCriado, editar(posicao)))
                .mostra();
    }

    @NonNull
    private ProdutoRepository.DadosCarregadosCallback<Produto> editar(int posicao) {
        return new ProdutoRepository.DadosCarregadosCallback<Produto>() {
            @Override
            public void quandoSucesso(Produto produtoEditado) {
                adapter.edita(posicao, produtoEditado);
            }
            @Override
            public void quandoFalha(String erro) {
                Toast.makeText(ListaProdutosActivity.this, RESPOSTA_ERRO_EDITAR, Toast.LENGTH_SHORT).show();
            }
        };
    }

}
