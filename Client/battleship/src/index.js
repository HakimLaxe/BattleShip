import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import { setupMocks } from './mock/apiMock'
import { ENV } from './contants/constants'

if (ENV === 'TEST') {
  setupMocks();
}

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
