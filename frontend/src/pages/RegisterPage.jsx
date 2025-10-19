import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const RegisterPage = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    username: "",
    password: "",
  });
  const [errors, setErrors] = useState([]);

  const handleChange = (e) => {
    const { name, value } = e.target; // ✅ added missing space after `const`
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setErrors([]);

    try {
      await axios.post("/api/auth/register", formData);

      // ✅ Navigate to login with success message
      navigate("/login", {
        state: { message: "Registration successful! Please log in." },
      });
    } catch (error) {
      if (error.response?.data) {
        const data = error.response.data;

        if (typeof data === "string") {
          setErrors([data]);
        } else if (typeof data === "object") {
          const errorMessages = Object.entries(data).map(
            ([field, message]) => `${field}: ${message}`
          );
          setErrors(errorMessages);
        } else {
          setErrors(["An unknown error occurred during registration."]);
        }
      } else {
        setErrors(["Network error. Please try again later."]);
      }
    }
  };

  return (
    <div className="register-page">
      <h2>Register</h2>

      {errors.length > 0 && (
        <div
          className="alert alert-danger"
          style={{
            backgroundColor: "#f8d7da",
            color: "#842029",
            padding: "10px",
            borderRadius: "5px",
            marginBottom: "15px", // ✅ fixed typo: was "margitBottom"
          }}
        >
          {errors.map((error, index) => (
            <p key={index} style={{ margin: 0 }}>
              {error}
            </p>
          ))}
        </div>
      )}

      <form onSubmit={handleSubmit}>
        <div className="form-group" style={{ marginBottom: "10px" }}>
          <label htmlFor="username">Username:</label>
          <input
            type="text"
            id="username"
            name="username"
            value={formData.username}
            onChange={handleChange}
            required
            minLength={3}
            maxLength={50}
            className="form-control"
            style={{
              width: "100%",
              padding: "8px",
              borderRadius: "4px",
              border: "1px solid #ccc",
            }}
          />
        </div>

        <div className="form-group" style={{ marginBottom: "10px" }}>
          <label htmlFor="password">Password:</label>
          <input
            type="password"
            id="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
            required
            minLength={6}
            className="form-control"
            style={{
              width: "100%",
              padding: "8px",
              borderRadius: "4px",
              border: "1px solid #ccc",
            }}
          />
        </div>

        <button
          type="submit"
          className="btn btn-primary"
          style={{
            width: "100%",
            padding: "10px",
            backgroundColor: "#007bff",
            color: "#fff",
            border: "none",
            borderRadius: "5px",
            cursor: "pointer",
          }}
        >
          Register
        </button>
      </form>
    </div>
  );
};

export default RegisterPage;
