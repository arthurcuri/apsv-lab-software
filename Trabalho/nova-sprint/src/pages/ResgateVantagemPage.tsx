import { useEffect, useState } from "react";
import api from "../services/api";

interface Vantagem {
  id: number;
  nome: string;
  descricao: string;
  imagemUrl: string;
  custo: number;
}

function ResgateVantagemPage() {
  const [saldo, setSaldo] = useState(0);
  const [vantagens, setVantagens] = useState<Vantagem[]>([]);
  const [codigoResgate, setCodigoResgate] = useState<string | null>(null);
  const [saldoAntes, setSaldoAntes] = useState<number | null>(null);
  const [saldoDepois, setSaldoDepois] = useState<number | null>(null);

  useEffect(() => {
    buscarSaldo();
    buscarVantagens();
  }, []);

  const buscarSaldo = async () => {
    try {
      const usuarioId = sessionStorage.getItem("usuarioId");
      if (!usuarioId) {
        alert("Usuário não logado!");
        return;
      }
      const response = await api.get(`/usuarios/${usuarioId}/saldo`);
      setSaldo(response.data.saldo);
    } catch (error) {
      console.error("Erro ao buscar saldo:", error);
    }
  };

  const buscarVantagens = async () => {
    try {
      // Geralmente a rota é apenas /vantagens
      const response = await api.get("/vantagens");
      setVantagens(response.data);
    } catch (error) {
      console.error("Erro ao buscar vantagens:", error);
    }
  };

  const gerarCodigo = () => {
    return Math.random().toString(36).substring(2, 10).toUpperCase();
  };

  const handleResgatar = async (vantagem: Vantagem) => {
    if (saldo < vantagem.custo) {
      alert("Saldo insuficiente para resgatar essa vantagem!");
      return;
    }

    const usuarioId = sessionStorage.getItem("usuarioId");
    if (!usuarioId) {
      alert("Usuário não logado!");
      return;
    }

    try {
      await api.post("/vantagens/resgatar", {
        usuarioId: Number(usuarioId),
        vantagemId: vantagem.id,
      });

      const novoSaldo = saldo - vantagem.custo;
      setSaldoAntes(saldo);
      setSaldoDepois(novoSaldo);
      setSaldo(novoSaldo);
      setCodigoResgate(gerarCodigo());

      alert("Vantagem resgatada com sucesso!");
    } catch (error) {
      console.error("Erro ao resgatar vantagem:", error);
      alert("Erro ao resgatar vantagem.");
    }
  };

  return (
    <div>
      <h1>Resgate de Vantagens</h1>

      {/* Saldo Atual */}
      <div style={{ marginBottom: "20px", fontSize: "18px" }}>
        <strong>Saldo Atual:</strong> {saldo}
      </div>

      {/* Vantagens */}
      <div style={{ display: "flex", flexWrap: "wrap", gap: "20px" }}>
        {vantagens.map((vantagem) => (
          <div key={vantagem.id} style={{ border: "1px solid black", padding: "10px", width: "250px" }}>
            <img src={vantagem.imagemUrl} alt={vantagem.nome} style={{ width: "100%", height: "150px", objectFit: "cover" }} />
            <h3>{vantagem.nome}</h3>
            <p>{vantagem.descricao}</p>
            <p><strong>Custo:</strong> {vantagem.custo} moedas</p>
            <button
              onClick={() => handleResgatar(vantagem)}
              style={{ padding: "8px", backgroundColor: "#4CAF50", color: "white", width: "100%", marginTop: "10px" }}
            >
              Resgatar
            </button>
          </div>
        ))}
      </div>

      {/* Exibir saldo antes, depois e código */}
      {codigoResgate && saldoAntes !== null && saldoDepois !== null && (
        <div style={{ marginTop: "40px", padding: "20px", border: "2px solid green" }}>
          <h2>Resumo do Resgate</h2>
          <p><strong>Saldo Anterior:</strong> {saldoAntes}</p>
          <p><strong>Saldo Atual:</strong> {saldoDepois}</p>
          <p><strong>Código de Resgate:</strong> {codigoResgate}</p>
        </div>
      )}
    </div>
  );
}

export default ResgateVantagemPage;