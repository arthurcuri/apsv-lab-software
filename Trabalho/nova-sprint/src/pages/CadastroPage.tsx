import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";

function CadastroPage() {
  const [nome, setNome] = useState("");
  const [email, setEmail] = useState("");
  const [cpf, setCpf] = useState("");
  const [senha, setSenha] = useState("");
  const [confirmarSenha, setConfirmarSenha] = useState("");

  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (senha !== confirmarSenha) {
      alert("As senhas não coincidem!");
      return;
    }

    try {
      const response = await api.post("/cadastrar", {
        nome,
        email,
        cpf,
        senha
      });
      console.log("Cadastro realizado com sucesso:", response.data);
      alert("Usuário cadastrado com sucesso!");
      navigate("/"); // Redirecionar para login
    } catch (error) {
      console.error("Erro no cadastro:", error);
      alert("Erro ao cadastrar usuário.");
    }
  };

  const handleVoltar = () => {
    navigate("/");
  };

  return (
    <div style={{ display: "flex", justifyContent: "center", alignItems: "center", height: "100vh" }}>
      <form onSubmit={handleSubmit} style={{ display: "flex", flexDirection: "column", width: "300px" }}>
        {/* Botão Voltar */}
        <button 
          type="button" 
          onClick={handleVoltar} 
          style={{
            alignSelf: "flex-start",
            marginBottom: "20px",
            backgroundColor: "#007bff",  // Azul
            color: "white",
            border: "none",
            fontSize: "24px",
            cursor: "pointer",
            padding: "8px 16px",
            borderRadius: "50%"         // Botão redondo
          }}
        >
          ←
        </button>

        <h2>Cadastrar</h2>
        <input
          type="text"
          placeholder="Nome"
          value={nome}
          onChange={(e) => setNome(e.target.value)}
          style={{ marginBottom: "10px", padding: "8px", borderRadius: "8px" }}
          required
        />
        <input
          type="email"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          style={{ marginBottom: "10px", padding: "8px", borderRadius: "8px" }}
          required
        />
        <input
          type="text"
          placeholder="CPF"
          value={cpf}
          onChange={(e) => setCpf(e.target.value)}
          style={{ marginBottom: "10px", padding: "8px", borderRadius: "8px" }}
          required
        />
        <input
          type="password"
          placeholder="Senha"
          value={senha}
          onChange={(e) => setSenha(e.target.value)}
          style={{ marginBottom: "10px", padding: "8px", borderRadius: "8px" }}
          required
        />
        <input
          type="password"
          placeholder="Confirmar Senha"
          value={confirmarSenha}
          onChange={(e) => setConfirmarSenha(e.target.value)}
          style={{ marginBottom: "10px", padding: "8px", borderRadius: "8px" }}
          required
        />
        <button
          type="submit"
          style={{
            padding: "10px",
            backgroundColor: "#4CAF50",
            color: "white",
            borderRadius: "8px"
          }}
        >
          Salvar dados
        </button>
      </form>
    </div>
  );
}

export default CadastroPage;