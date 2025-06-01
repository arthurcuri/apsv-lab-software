import { Routes, Route, useLocation } from 'react-router-dom'; // IMPORTANTE: useLocation
import Sidebar from './components/Sidebar';

import LoginPage from './pages/LoginPage';
import CadastroPage from './pages/CadastroPage';
import RecuperarSenhaPage from './pages/RecuperarSenhaPage';
import DashboardProfessorPage from './pages/DashboardProfessorPage';
import DashboardAlunoPage from './pages/DashboardAlunoPage';
import ExtratoPage from './pages/ExtratoPage';
import CadastroVantagemPage from './pages/CadastroVantagemPage';
import ResgateVantagemPage from './pages/ResgateVantagemPage';

function App() {
  const location = useLocation(); // Hook que dá a URL atual

  // Lista de páginas que NÃO terão Sidebar
  const paginasSemSidebar = ['/', '/cadastro', '/recuperar-senha'];

  // Verificar se a página atual precisa de Sidebar
  const exibirSidebar = !paginasSemSidebar.includes(location.pathname);

  return (
    <div style={{ display: 'flex' }}>
      {/* Condicionalmente exibir Sidebar */}
      {exibirSidebar && <Sidebar />}

      {/* Ajuste da margem */}
      <div style={{ marginLeft: exibirSidebar ? '270px' : '0', padding: '20px', width: '100%' }}>
        <Routes>
          <Route path="/" element={<LoginPage />} />
          <Route path="/cadastro" element={<CadastroPage />} />
          <Route path="/recuperar-senha" element={<RecuperarSenhaPage />} />
          <Route path="/dashboard-professor" element={<DashboardProfessorPage />} />
          <Route path="/dashboard-aluno" element={<DashboardAlunoPage />} />
          <Route path="/extrato" element={<ExtratoPage />} />
          <Route path="/cadastro-vantagem" element={<CadastroVantagemPage />} />
          <Route path="/resgate-vantagem" element={<ResgateVantagemPage />} />
        </Routes>
      </div>
    </div>
  );
}

export default App;