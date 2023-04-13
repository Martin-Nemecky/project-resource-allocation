import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import reportWebVitals from './reportWebVitals';

// Bootstrap CSS
import "bootstrap/dist/css/bootstrap.min.css";
// Bootstrap Bundle JS
import "bootstrap/dist/js/bootstrap.bundle.min";
import { ScheduleProvider } from './contexts/ScheduleContext';
import { StatusProvider } from './contexts/StatusContext';
import { ScheduleHistoryProvider } from './contexts/HistoryContext';


const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);

root.render(
  <React.StrictMode>
    <ScheduleProvider>
      <StatusProvider>
        <ScheduleHistoryProvider>
          <App />
        </ScheduleHistoryProvider>
      </StatusProvider>
    </ScheduleProvider>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
