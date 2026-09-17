// Base URL of the Spring Boot backend
const API_BASE_URL = "http://localhost:8080/api";

const Auth = {
  getToken() {
    return localStorage.getItem("token");
  },
  setSession(data) {
    localStorage.setItem("token", data.token);
    localStorage.setItem("user", JSON.stringify({
      id: data.id, fullName: data.fullName, email: data.email, role: data.role
    }));
  },
  getUser() {
    const raw = localStorage.getItem("user");
    return raw ? JSON.parse(raw) : null;
  },
  isLoggedIn() {
    return !!this.getToken();
  },
  isAdmin() {
    const user = this.getUser();
    return user && user.role === "ROLE_ADMIN";
  },
  logout() {
    localStorage.removeItem("token");
    localStorage.removeItem("user");
    window.location.href = "login.html";
  }
};

// Generic API call wrapper
async function apiFetch(path, options = {}) {
  const headers = options.headers || {};
  headers["Content-Type"] = "application/json";

  const token = Auth.getToken();
  if (token) {
    headers["Authorization"] = "Bearer " + token;
  }

  let response;
  try {
    response = await fetch(API_BASE_URL + path, { ...options, headers });
  } catch (err) {
    throw new Error("Cannot reach the server. Make sure the Spring Boot backend is running on http://localhost:8080");
  }

  const contentType = response.headers.get("content-type") || "";
  const body = contentType.includes("application/json") ? await response.json() : null;

  if (!response.ok) {
    const message = body && (body.message || Object.values(body)[0]) ? (body.message || Object.values(body)[0]) : "Something went wrong";
    throw new Error(message);
  }

  return body;
}

// ---- Shared navbar auth-state rendering ----
function renderNavAuthState() {
  const authArea = document.getElementById("nav-auth-area");
  if (!authArea) return;

  if (Auth.isLoggedIn()) {
    const user = Auth.getUser();
    authArea.innerHTML = `
      <span style="color: var(--text-muted); font-size:0.9rem;">Hi, ${user.fullName.split(" ")[0]}</span>
      ${user.role === "ROLE_ADMIN" ? '<a href="admin.html" class="btn btn-outline btn-small">Admin Panel</a>' : '<a href="my-bookings.html" class="btn btn-outline btn-small">My Bookings</a>'}
      <button class="btn btn-primary btn-small" onclick="Auth.logout()">Logout</button>
    `;
  } else {
    authArea.innerHTML = `
      <a href="login.html" class="btn btn-outline btn-small">Login</a>
      <a href="register.html" class="btn btn-primary btn-small">Book Now</a>
    `;
  }
}

document.addEventListener("DOMContentLoaded", renderNavAuthState);
