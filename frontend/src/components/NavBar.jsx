import React from "react";
import { Link, useLocation } from "react-router-dom";

const NavBar = () => {
  const location = useLocation();

  return (
    <nav
      className="navbar"
      style={{
        backgroundColor: "#f8f9fa",
        padding: "10px 20px",
        borderBottom: "1px solid #ddd",
      }}
    >
      <div
        className="container"
        style={{
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
        }}
      >
        <Link
          to="/"
          className="navbar-brand"
          style={{
            fontWeight: "bold",
            fontSize: "18px",
            textDecoration: "none",
            color: "#007bff",
          }}
        >
          Learning Management System
        </Link>

        <div className="nav-links" style={{ display: "flex", gap: "10px" }}>
          {location.pathname !== "/register" && (
            <Link
              to="/register"
              className="nav-link"
              style={{ textDecoration: "none", color: "#007bff" }}
            >
              Register
            </Link>
          )}
          {location.pathname !== "/login" && (
            <Link
              to="/login"
              className="nav-link"
              style={{ textDecoration: "none", color: "#007bff" }}
            >
              Login
            </Link>
          )}
        </div>
      </div>
    </nav>
  );
};

export default NavBar;
