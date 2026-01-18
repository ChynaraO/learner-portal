const handleSubmit = async (e) => {
  e.preventDefault();
  setError("");

  try {
    const response = await axios.post("/api/auth/login", formData);

    localStorage.setItem("token", response.data.token);
    navigate("/");
  } catch (error) {
    setError(error.response?.data?.error || "Login failed");
  }
};
