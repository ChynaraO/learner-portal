import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/axios";

const RegisterPage = () => {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    username: "",
    password: "",
    email: "",
  });

  const [errors, setErrors] = useState([]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setErrors([]);

    try {
      await api.post("/api/auth/register", formData);
      navigate("/login");
    } catch (err) {
      if (err.response?.data) {
        const data = err.response.data;
        if (typeof data === "object") {
          setErrors(Object.values(data));
        } else {
          setErrors([data]);
        }
      } else {
        setErrors(["Network error"]);
      }
    }
  };

  return (
    <div>
      <h2>Register</h2>

      {errors.map((e, i) => (
        <p key={i} style={{ color: "red" }}>{e}</p>
      ))}

      <form onSubmit={handleSubmit}>
        <input
          name="username"
          placeholder="Username"
          value={formData.username}
          onChange={handleChange}
          required
        />

        <input
          name="email"
          type="email"
          placeholder="Email"
          value={formData.email}
          onChange={handleChange}
          required
        />

        <input
          name="password"
          type="password"
          placeholder="Password"
          value={formData.password}
          onChange={handleChange}
          required
        />

        <button type="submit">Register</button>
      </form>
    </div>
  );
};

export default RegisterPage;
