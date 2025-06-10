import { useState } from "react";
import api from "../services/api";

function CadastroVantagemPage() {
  const [nome, setNome] = useState("");
  const [descricao, setDescricao] = useState("");
  const [imagem, setImagem] = useState("");
  const [custo, setCusto] = useState(0);
  const [errors, setErrors] = useState<{ [key: string]: string }>({});

  const handleCadastro = async (e: React.FormEvent) => {
    e.preventDefault();
    setErrors({});
    try {
      await api.post("/vantagens", {
        nome,
        descricao,
        imagem, // Corrigido para o nome esperado pelo backend
        custo
      });
      alert("Vantagem cadastrada com sucesso!");
      setNome("");
      setDescricao("");
      setImagem("");
      setCusto(0);
    } catch (error: any) {
      if (error.response && error.response.status === 400 && error.response.data) {
        setErrors(error.response.data);
      } else {
        alert("Erro ao cadastrar vantagem.");
      }
    }
  };

  return (
    <div>
      <h1>Cadastro de Vantagem</h1>

      <form onSubmit={handleCadastro} style={{ display: "flex", flexDirection: "column", width: "400px" }}>
        <input
          type="text"
          placeholder="Nome"
          value={nome}
          onChange={(e) => setNome(e.target.value)}
          style={{ marginBottom: "10px", padding: "8px" }}

        />
        {errors.nome && <span style={{ color: "red", marginBottom: "10px" }}>{errors.nome}</span>}

        <textarea
          placeholder="Descrição"
          value={descricao}
          onChange={(e) => setDescricao(e.target.value)}
          style={{ marginBottom: "10px", padding: "8px", height: "100px" }}

        />
        {errors.descricao && <span style={{ color: "red", marginBottom: "10px" }}>{errors.descricao}</span>}

        <input
          type="text"
          placeholder="URL da Imagem"
          value={imagem}
          onChange={(e) => setImagem(e.target.value)}
          style={{ marginBottom: "10px", padding: "8px" }}

        />
        {errors.imagem && <span style={{ color: "red", marginBottom: "10px" }}>{errors.imagem}</span>}

        <input
          type="number"
          placeholder="Custo (moedas)"
          value={custo}
          onChange={(e) => setCusto(Number(e.target.value))}
          style={{ marginBottom: "10px", padding: "8px" }}

        />
        {errors.custo && <span style={{ color: "red", marginBottom: "10px" }}>{errors.custo}</span>}

        <button
          type="submit"
          style={{ padding: "10px", backgroundColor: "#4CAF50", color: "white", fontWeight: "bold" }}
        >
          Cadastrar Vantagem
        </button>
      </form>
    </div>
  );
}

export default CadastroVantagemPage;