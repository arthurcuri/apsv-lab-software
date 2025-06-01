import { useState } from "react";
import api from "../services/api";
import { useNavigate } from "react-router-dom";

function RecuperarSenhaPage() {
  const [email, setEmail] = useState("");
  const navigate = useNavigate();

  const handleRecuperarSenha = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      const response = await api.post("/recuperar-senha", { email });
      console.log("Solicitação de recuperação enviada:", response.data);
      alert("Instruções de recuperação enviadas para seu email!");
      navigate("/"); // Redirecionar de volta para login
    } catch (error) {
      console.error("Erro ao recuperar senha:", error);
      alert("Erro ao solicitar recuperação de senha.");
    }
  };

  const handleVoltar = () => {
    navigate("/");
  };

  return (
    <div style={{ display: "flex", justifyContent: "center", alignItems: "center", height: "100vh" }}>
      <form onSubmit={handleRecuperarSenha} style={{ display: "flex", flexDirection: "column", width: "300px" }}>
        {/* Botão Voltar */}
        <button 
          type="button" 
          onClick={handleVoltar} 
          style={{
            alignSelf: "flex-start",
            marginBottom: "20px",
            backgroundColor: "#007bff",     // Azul
            color: "white",
            border: "none",
            fontSize: "24px",
            cursor: "pointer",
            padding: "8px 16px",
            borderRadius: "50%"             // Deixa ele redondo
          }}
        >
          ←
        </button>

        <h2>Recuperar senha</h2>

        <input
          type="email"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          style={{ marginBottom: "10px", padding: "8px", borderRadius: "8px" }}
          required
        />
        <button type="submit" style={{ padding: "10px", backgroundColor: "#4CAF50", color: "white", borderRadius: "8px" }}>
          Recuperar Senha
        </button>
      </form>
    </div>
  );
}

export default RecuperarSenhaPage;