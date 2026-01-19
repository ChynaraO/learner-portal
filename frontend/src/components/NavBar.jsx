import React from "react";
import { Link } from "react-router-dom";

const NavBar = () => {
  return (
    <nav style={{ padding: "10px", background: "#f5f5f5" }}>
      <Link to="/" style={{ marginRight: "10px" }}>Home</Link>
      <Link to="/register" style={{ marginRight: "10px" }}>Register</Link>
      <Link to="/login">Login</Link>
    </nav>
  );
};

export default NavBar;
