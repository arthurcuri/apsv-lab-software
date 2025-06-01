import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080', // aqui é o endereço do seu backend Spring Boot
});

export default api;