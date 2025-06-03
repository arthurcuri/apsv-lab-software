import { useEffect, useState } from "react";
import api from "../services/api";

interface Usuario {
  id: number;
  nome: string;
}

interface Historico {
  id: number;
  origemId: number;
  quantidade: number;
  motivo: string;
  data: string;
}

interface Vantagem {
  id: number;
  nome: string;
  descricao: string;
  imagem: string;
  custo: number;
}

function DashboardAlunoPage() {
  const [saldo, setSaldo] = useState(0);
  const [historico, setHistorico] = useState<Historico[]>([]);
  const [professores, setProfessores] = useState<Usuario[]>([]);
  const [vantagens, setVantagens] = useState<Vantagem[]>([]);

  const usuarioId = sessionStorage.getItem("usuarioId");

  useEffect(() => {
    if (usuarioId) {
      buscarSaldo();
      buscarHistorico();
      buscarVantagens();
      buscarProfessores();
    }
  }, []);

  const buscarSaldo = async () => {
    try {
      const response = await api.get(`/usuarios/${usuarioId}/saldo`);
      setSaldo(response.data.saldo);
    } catch (error) {
      console.error("Erro ao buscar saldo:", error);
    }
  };

  const buscarHistorico = async () => {
    try {
      const response = await api.get(`/usuarios/${usuarioId}/historico-recebidos`);
      setHistorico(response.data);
    } catch (error) {
      console.error("Erro ao buscar histórico:", error);
    }
  };

  const buscarProfessores = async () => {
    try {
      const response = await api.get("/usuarios"); // ou /usuarios?tipo=PROFESSOR se houver filtro
      setProfessores(response.data.filter((u: any) => u.tipo === "PROFESSOR"));
    } catch (error) {
      console.error("Erro ao buscar professores:", error);
    }
  };

  const buscarVantagens = async () => {
    try {
      const response = await api.get("/vantagens");
      setVantagens(response.data);
    } catch (error) {
      console.error("Erro ao buscar vantagens:", error);
    }
  };

  const handleResgatar = async (idVantagem: number) => {
    try {
      await api.post(`/aluno/resgatar/${idVantagem}`);
      alert("Vantagem resgatada com sucesso!");
      buscarSaldo();
    } catch (error) {
      console.error("Erro ao resgatar vantagem:", error);
      alert("Erro ao resgatar vantagem.");
    }
  };

  return (
    <div>
      <h1>Dashboard do Aluno</h1>

      {/* Saldo */}
      <div style={{ marginBottom: "20px", fontSize: "18px" }}>
        <strong>Saldo de Moedas:</strong> {saldo}
      </div>

      {/* Histórico */}
      <h2>Histórico de Recebimentos</h2>
      <table style={{ width: "100%", borderCollapse: "collapse", marginBottom: "40px" }}>
        <thead>
          <tr>
            <th style={{ border: "1px solid black", padding: "8px" }}>Professor</th>
            <th style={{ border: "1px solid black", padding: "8px" }}>Quantidade</th>
            <th style={{ border: "1px solid black", padding: "8px" }}>Motivo</th>
            <th style={{ border: "1px solid black", padding: "8px" }}>Data</th>
          </tr>
        </thead>
        <tbody>
          {historico.map((item) => {
            const professor = professores.find((p) => p.id === item.origemId);
            const nomeProfessor = professor ? professor.nome : "Desconhecido";
            let dataFormatada = "Data não disponível";
            if (item.data) {
              const data = new Date(item.data);
              if (!isNaN(data.getTime())) {
                dataFormatada = data.toLocaleString("pt-BR", {
                  day: "2-digit",
                  month: "2-digit",
                  year: "numeric",
                  hour: "2-digit",
                  minute: "2-digit",
                  second: "2-digit"
                });
              }
            }
            return (
              <tr key={item.id}>
                <td style={{ border: "1px solid black", padding: "8px" }}>{nomeProfessor}</td>
                <td style={{ border: "1px solid black", padding: "8px" }}>{item.quantidade}</td>
                <td style={{ border: "1px solid black", padding: "8px" }}>{item.motivo}</td>
                <td style={{ border: "1px solid black", padding: "8px" }}>{dataFormatada}</td>
              </tr>
            );
          })}
        </tbody>
      </table>

      {/* Vantagens */}
      <h2>Vantagens Disponíveis</h2>
      <div style={{ display: "flex", flexWrap: "wrap", gap: "20px" }}>
        {vantagens.map((vantagem) => (
          <div key={vantagem.id} style={{ border: "1px solid black", padding: "10px", width: "250px" }}>
            <img src={vantagem.imagem} alt={vantagem.nome} style={{ width: "100%", height: "150px", objectFit: "cover" }} />
            <h3>{vantagem.nome}</h3>
            <p>{vantagem.descricao}</p>
            <p><strong>Custo:</strong> {vantagem.custo} moedas</p>
            <button
              onClick={() => handleResgatar(vantagem.id)}
              style={{ padding: "8px", backgroundColor: "#4CAF50", color: "white", width: "100%" }}
            >
              Resgatar
            </button>
          </div>
        ))}
      </div>
    </div>
  );
}

export default DashboardAlunoPage;