import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

function SidebarAluno() {
  const [recolhido, setRecolhido] = useState(false);
  const navigate = useNavigate();

  const toggleSidebar = () => {
    setRecolhido(!recolhido);
  };

  const handleLogout = () => {
    localStorage.clear();
    navigate("/");
  };

  return (
    <div
      style={{
        width: recolhido ? "70px" : "250px",
        height: "100vh",
        backgroundColor: "#333",
        color: "white",
        display: "flex",
        flexDirection: "column",
        justifyContent: "space-between",
        padding: "20px 10px",
        position: "fixed",
        top: 0,
        left: 0,
        overflow: "hidden", // <- importante
      }}
    >
      <div style={{ flex: "1", overflowY: "auto" }}>
        {/* Botão de expandir/recolher */}
        <div
          onClick={toggleSidebar}
          style={{
            cursor: "pointer",
            fontSize: "24px",
            marginBottom: "40px",
            textAlign: "center"
          }}
        >
          ≡
        </div>

        {/* Itens do menu */}
        <nav style={{ display: "flex", flexDirection: "column", gap: "20px" }}>
          <Link to="/dashboard-aluno" style={linkStyle}>
            {recolhido ? "🎓" : "Dashboard Aluno"}
          </Link>
          <Link to="/extrato" style={linkStyle}>
            {recolhido ? "📄" : "Consulta de Extrato"}
          </Link>
          <Link to="/resgate-vantagem" style={linkStyle}>
            {recolhido ? "🎁" : "Resgate de Vantagens"}
          </Link>
        </nav>
      </div>

      {/* Botão de Sair */}
      <div style={{ marginBottom: "20px" }}>
        <button
          onClick={handleLogout}
          style={{
            padding: "10px",
            backgroundColor: "red",
            color: "white",
            fontWeight: "bold",
            border: "none",
            cursor: "pointer",
            width: "100%",
          }}
        >
          {recolhido ? "🚪" : "Sair"}
        </button>
      </div>
    </div>
  );
}

const linkStyle: React.CSSProperties = {
  color: "white",
  textDecoration: "none",
  fontSize: "18px",
  textAlign: "center" as const
};

export default SidebarAluno;