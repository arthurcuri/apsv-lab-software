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
    if (!usuarioId) {
      alert("Usuário não logado!");
      return;
    }

    try {
      await api.post(`/vantagens/resgatar`, {
        usuarioId: Number(usuarioId),
        vantagemId: idVantagem
      });
      alert("Vantagem resgatada com sucesso!");
      buscarSaldo();
    } catch (error) {
      console.error("Erro ao resgatar vantagem:", error);
      alert("Erro ao resgatar vantagem.");
    }
  };

  return (
    <div style={{ padding: "20px" }}>
      <h1>Dashboard do Aluno</h1>

      <div style={{ display: "flex", justifyContent: "space-between", alignItems: "flex-start" }}>
        
        {/* Histórico do lado esquerdo */}
        <div style={{ flex: 1 }}>
          <h2>Histórico de Recebimentos</h2>
          <table style={{ width: "100%", borderCollapse: "collapse" }}>
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
                const data = new Date(item.data);
                const dataFormatada = !isNaN(data.getTime())
                  ? data.toLocaleString("pt-BR", {
                      day: "2-digit",
                      month: "2-digit",
                      year: "numeric",
                      hour: "2-digit",
                      minute: "2-digit",
                      second: "2-digit"
                    })
                  : "Data não disponível";

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
        </div>

        {/* Área da direita com saldo e vantagens */}
        <div style={{ width: "350px", marginLeft: "20px" }}>
          {/* Saldo */}
          <div
            style={{
              backgroundColor: "#f0f0f0",
              padding: "15px",
              borderRadius: "8px",
              marginBottom: "20px",
              textAlign: "center",
              fontSize: "18px",
              color: "#000" // ✅ Texto preto no saldo
            }}
          >
            <strong>Saldo de Moedas:</strong> {saldo}
          </div>

          {/* Vantagens */}
          <h2>Vantagens Disponíveis</h2>
          <div style={{ display: "flex", flexDirection: "column", gap: "15px" }}>
            {vantagens.map((vantagem) => (
              <div key={vantagem.id} style={{ border: "1px solid black", padding: "10px", borderRadius: "8px" }}>
                <img
                  src={vantagem.imagem}
                  alt={vantagem.nome}
                  style={{ width: "100%", height: "150px", objectFit: "cover" }}
                />
                <h3>{vantagem.nome}</h3>
                <p>{vantagem.descricao}</p>
                <p>
                  <strong>Custo:</strong> {vantagem.custo} moedas
                </p>
                
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}

export default DashboardAlunoPage;