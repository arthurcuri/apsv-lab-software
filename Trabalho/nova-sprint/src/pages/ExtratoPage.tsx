import { useEffect, useState } from "react";
import api from "../services/api";

interface Transacao {
  id: number;
  tipo: "ENVIO" | "RECEBIMENTO";
  participante: string;
  quantidade: number;
  motivo: string;
  data: string;
}

function ExtratoPage() {
  const [extratoCompleto, setExtratoCompleto] = useState<Transacao[]>([]);
  const [filtro, setFiltro] = useState<"TODOS" | "ENVIO" | "RECEBIMENTO">("TODOS");

  useEffect(() => {
    buscarExtrato();
  }, []);

  const buscarExtrato = async () => {
    const usuarioId = sessionStorage.getItem("usuarioId");
    if (!usuarioId) {
      alert("Usuário não logado!");
      return;
    }
    try {
      // Busca envios (como origem)
      const envios = await api.get(`/usuarios/${usuarioId}/historico`);
      // Busca recebimentos (como destino)
      const recebimentos = await api.get(`/usuarios/${usuarioId}/historico-recebidos`);

      // Mapeia para o formato esperado
      const transacoesEnvio: Transacao[] = envios.data.map((t: any) => ({
        id: t.id,
        tipo: "ENVIO",
        participante: t.destinoId, // será substituído pelo nome abaixo
        quantidade: t.quantidade,
        motivo: t.motivo,
        data: t.data,
      }));

      const transacoesRecebimento: Transacao[] = recebimentos.data.map((t: any) => ({
        id: t.id,
        tipo: "RECEBIMENTO",
        participante: t.origemId, // será substituído pelo nome abaixo
        quantidade: t.quantidade,
        motivo: t.motivo,
        data: t.data,
      }));

      // Busca nomes dos participantes (opcional, mas recomendado)
      // Você pode otimizar buscando todos usuários de uma vez, aqui é um exemplo simples:
      const usuariosCache: Record<number, string> = {};

      async function getNomeUsuario(id: number) {
        if (usuariosCache[id]) return usuariosCache[id];
        try {
          const res = await api.get(`/usuarios/${id}`);
          usuariosCache[id] = res.data.nome;
          return res.data.nome;
        } catch {
          return "Desconhecido";
        }
      }

      // Preenche nomes dos participantes
      for (const t of transacoesEnvio) {
        t.participante = await getNomeUsuario(t.participante as unknown as number);
      }
      for (const t of transacoesRecebimento) {
        t.participante = await getNomeUsuario(t.participante as unknown as number);
      }

      setExtratoCompleto([...transacoesEnvio, ...transacoesRecebimento].sort((a, b) => new Date(b.data).getTime() - new Date(a.data).getTime()));
    } catch (error) {
      console.error("Erro ao buscar extrato:", error);
      alert("Erro ao carregar o extrato.");
    }
  };

  const transacoesFiltradas = extratoCompleto.filter((transacao) => {
    if (filtro === "TODOS") {
      return true;
    }
    return transacao.tipo === filtro;
  });

  return (
    <div>
      <h1>Consulta de Extrato</h1>
      <div style={{ marginBottom: "20px" }}>
        <label htmlFor="filtro" style={{ marginRight: "10px" }}>Filtrar por tipo:</label>
        <select
          id="filtro"
          value={filtro}
          onChange={(e) => setFiltro(e.target.value as "TODOS" | "ENVIO" | "RECEBIMENTO")}
          style={{ padding: "8px" }}
        >
          <option value="TODOS">Todos</option>
          <option value="ENVIO">Débito (Enviados)</option>
          <option value="RECEBIMENTO">Crédito (Recebidos)</option>
        </select>
      </div>
      <table style={{ width: "100%", borderCollapse: "collapse", marginTop: "20px" }}>
        <thead>
          <tr>
            <th style={{ border: "1px solid black", padding: "8px" }}>Tipo</th>
            <th style={{ border: "1px solid black", padding: "8px" }}>Participante</th>
            <th style={{ border: "1px solid black", padding: "8px" }}>Quantidade</th>
            <th style={{ border: "1px solid black", padding: "8px" }}>Motivo</th>
            <th style={{ border: "1px solid black", padding: "8px" }}>Data</th>
          </tr>
        </thead>
        <tbody>
          {transacoesFiltradas.map((transacao) => (
            <tr key={transacao.id}>
              <td style={{ border: "1px solid black", padding: "8px" }}>
                {transacao.tipo === "ENVIO" ? "Débito" : "Crédito"}
              </td>
              <td style={{ border: "1px solid black", padding: "8px" }}>{transacao.participante}</td>
              <td style={{ border: "1px solid black", padding: "8px" }}>{transacao.quantidade}</td>
              <td style={{ border: "1px solid black", padding: "8px" }}>{transacao.motivo}</td>
              <td style={{ border: "1px solid black", padding: "8px" }}>
                {new Date(transacao.data).toLocaleString("pt-BR", {
                  day: "2-digit",
                  month: "2-digit",
                  year: "numeric",
                  hour: "2-digit",
                  minute: "2-digit",
                  second: "2-digit"
                })}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default ExtratoPage;