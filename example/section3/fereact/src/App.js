
import React from 'react';
import {
  BrowserRouter, Navigate,
  Route, Routes
} from "react-router-dom";
import { routes } from './AppRoutes';
import AppLayout from "./components/layout/AppLayout";
import AuthenProvider from './components/provider/AuthenProvider';
import Login from "./screen/login/Login";

function App() {
  return (
    <AuthenProvider>
      <BrowserRouter basename="/">
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/" element={<AppLayout />}>
            <Route index element={<Navigate to="/accounts" replace />} />
            {routes.map((route, idx) => {
              return (
                <Route
                  key={idx}
                  path={route.path}
                  element={route.component} />
              );
            })}
          </Route>
          <Route path='*' element={<Navigate to={"/login"} />} />
        </Routes>
      </BrowserRouter>
    </AuthenProvider>
  );
}

export default App;
