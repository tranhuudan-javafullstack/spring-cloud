/* eslint-disable react-hooks/exhaustive-deps */
import axios from 'axios';
import React, { useContext, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { SERVER } from '../../api/API';
import { AuthenContext } from '../../components/provider/AuthenProvider';
import './login.css';

export default function Login() {
  let navigate = useNavigate();
  let authCtx = useContext(AuthenContext);

  useEffect(() => {
    checkAuthorizationCode();
  }, []);

  let login = () => {
    const authURL = `${SERVER.auth_uri}/protocol/openid-connect/auth?client_id=${SERVER.clientId}&scope=${SERVER.scopes}&redirect_uri=${SERVER.callback_uri}&response_type=code`;
    window.location.href = (authURL);
  };

  let checkAuthorizationCode = () => {
    let authorizationCode = getUrlParameter("code");
    if (authorizationCode)
      getAccessToken(authorizationCode);
  };

  let getAccessToken = async (code) => {
    const data = new URLSearchParams();
    data.append('grant_type', 'authorization_code');
    data.append('code', code);
    data.append('redirect_uri', SERVER.callback_uri);

    const headers = {
      'Authorization': 'Basic ' + btoa(SERVER.clientId + ":" + SERVER.clientSecret),
      'Content-Type': 'application/x-www-form-urlencoded'
    };

    var config = {
      baseURL: SERVER.auth_uri,
      method: 'post',
      url: '/protocol/openid-connect/token',
      headers,
      data
    };

    let response = await axios(config);

    let token = response.data;
    authCtx.login(token);
    navigate("/");
  };

  let getUrlParameter = (sParam) => {
    var sPageURL = window.location.search.substring(1);
    var sURLVariables = sPageURL.split('&');
    var sParameterName;
    var i;

    for (i = 0; i < sURLVariables.length; i++) {
      sParameterName = sURLVariables[i].split('=');

      if (sParameterName[0] === sParam) {
        return typeof sParameterName[1] === "undefined" ? true : decodeURIComponent(sParameterName[1]);
      }
    }
    return false;
  };

  return (
    <div className="container h-80">
      <div className="row align-items-center h-100">
        <div className="col-3 mx-auto mt-5">
          <div className="text-center">
            <img alt="profile-img" className="rounded-circle profile-img-card" src="/anonymous.png" height={200} />
            <form className="form-signin">
              <button onClick={login} className="btn btn-lg btn-primary btn-block btn-signin" type="button">LOGIN TO JMASTER.IO</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}