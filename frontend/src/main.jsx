
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.js'
import 'bootstrap-icons/font/bootstrap-icons.css'
import {BrowserRouter} from "react-router-dom";
import { AppProvider } from './context/AppContext.jsx';


createRoot(document.getElementById('root')).render(
  <BrowserRouter>
      <AppProvider>
          <App />
      </AppProvider>
  </BrowserRouter>

)
