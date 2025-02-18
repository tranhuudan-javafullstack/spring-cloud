import React from "react";
import { Navigate, Outlet } from "react-router-dom";
import Footer from "./Footer";
import TopHeader from "./TopHeader";

export default function AppLayout() {
  let token = localStorage.getItem("TOKEN");

  if (token)
    return <>
      <TopHeader />
      <div className="container-fluid">
        <div className="row">
          <Outlet />
        </div>
        <div className="row">
          <Footer />
        </div>
      </div>
    </>;

  return <Navigate to="/login" replace />;
}
