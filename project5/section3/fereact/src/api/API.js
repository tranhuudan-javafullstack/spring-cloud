import axios from 'axios';

export const SERVER = {
  resource_uri: `${process.env.REACT_APP_RESOUCE_URI}`,
  clientId: `${process.env.REACT_APP_KEYCLOAK_CLIENT_ID}`,
  clientSecret: `${process.env.REACT_APP_KEYCLOAK_CLIENT_SECRET}`,
  scopes: `${process.env.REACT_APP_KEYCLOAK_CLIENT_SCOPE}`,
  callback_uri: "http://127.0.0.1:3000/login",
  auth_uri: `${process.env.REACT_APP_KEYCLOAK_URI}`,
  auth_admin_uri: `${process.env.REACT_APP_KEYCLOAK_ADMIN_URI}`
};

const axiosAuthInstance = axios.create({
  baseURL: SERVER.resource_uri,
  timeout: 2500
});

export const axiosAuthKeycloak = axios.create({
  baseURL: SERVER.auth_admin_uri,
  timeout: 2500
});

const handleRequest = async (config) => {
  const value = localStorage.getItem("TOKEN");
  const token = JSON.parse(value);
  config.headers = {
    'Authorization': `Bearer ${token.access_token}`,
    'Accept': 'application/json'
  };
  return config;
}

const handleError = async (error) => {
  const originalRequest = error.config;
  if ((error.response.status === 401) && !originalRequest._retry) {
    originalRequest._retry = true;
    // const token =
    await refreshAccessToken();
    //axiosAuthInstance.defaults.headers.common['Authorization'] = 'Bearer ' + token.access_token;
    return axiosAuthInstance(originalRequest);
  }

  return Promise.reject(error);
}
// Request interceptor for API calls
axiosAuthInstance.interceptors.request.use(
  handleRequest,
  error => {
    Promise.reject(error);
  });

axiosAuthKeycloak.interceptors.request.use(
  handleRequest,
  error => {
    Promise.reject(error);
  });
// Response interceptor for API calls
axiosAuthInstance.interceptors.response.use((response) => {
  return response;
}, handleError);

axiosAuthKeycloak.interceptors.response.use((response) => {
  return response;
}, handleError);

export const refreshAccessToken = async () => {
  let value = localStorage.getItem("TOKEN");
  let refresh_token = JSON.parse(value).refresh_token;

  const params = new URLSearchParams();
  params.append('grant_type', 'refresh_token');
  params.append('refresh_token', refresh_token);

  const headers = {
    'Authorization': 'Basic ' + btoa(SERVER.clientId + ":" + SERVER.clientSecret),
    'Content-Type': 'application/x-www-form-urlencoded'
  };

  var config = {
    baseURL: SERVER.auth_uri,
    method: 'post',
    url: '/protocol/openid-connect/token',
    headers,
    data: params
  };

  let response = await axios(config);
  let data = response.data;
  //set new token
  localStorage.setItem("TOKEN", JSON.stringify(data));

  return data;

};

export default axiosAuthInstance;