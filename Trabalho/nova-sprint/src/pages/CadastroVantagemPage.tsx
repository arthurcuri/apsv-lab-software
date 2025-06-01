import { useState } from "react";
import api from "../services/api";

function CadastroVantagemPage() {
  const [nome, setNome] = useState("");
  const [descricao, setDescricao] = useState("");
  const [imagemUrl, setImagemUrl] = useState("");
  const [custo, setCusto] = useState(0);

  const handleCadastro = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await api.post("/vantagens", {
        nome,
        descricao,
        imagemUrl,
        custo
      });
      alert("Vantagem cadastrada com sucesso!");
      // Limpar o formulário
      setNome("");
      setDescricao("");
      setImagemUrl("");
      setCusto(0);
    } catch (error) {
      console.error("Erro ao cadastrar vantagem:", error);
      alert("Erro ao cadastrar vantagem.");
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
          required
        />
        <textarea
          placeholder="Descrição"
          value={descricao}
          onChange={(e) => setDescricao(e.target.value)}
          style={{ marginBottom: "10px", padding: "8px", height: "100px" }}
          required
        />
        <input
          type="text"
          placeholder="URL da Imagem"
          value={imagemUrl}
          onChange={(e) => setImagemUrl(e.target.value)}
          style={{ marginBottom: "10px", padding: "8px" }}
          required
        />
        <input
          type="number"
          placeholder="Custo (moedas)"
          value={custo}
          onChange={(e) => setCusto(Number(e.target.value))}
          style={{ marginBottom: "10px", padding: "8px" }}
          required
        />
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