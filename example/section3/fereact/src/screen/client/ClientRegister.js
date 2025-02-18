import { useEffect, useState } from "react";
import {axiosAuthKeycloak} from "../../api/API";

export default function ClientRegister() {

  let [clients, setClients] = useState([]);

  useEffect(() => {
    fetchData();
  }, []);

  let fetchData = () => {
    axiosAuthKeycloak.get("/clients")
      .then(function (response) {
        setClients(response.data);
        console.log(response.data);
      })
      .catch(function (error) {
        console.log(error);
      });
  };

  return <>
    <div className="col-12 mt-4 table-responsive">
      <table className="table table-bordered table-hover align-middle">
        <thead>
          <tr>
            <th >#Client ID</th>
            <th >#Client Name</th>
            <th >Scope</th>
            <th >Grant Types</th>
          </tr>
        </thead>
        <tbody>
          {clients.map(client => (
            <tr key={client.clientId}>
              <td >{client.clientId}</td>
              <td >{client.name}</td>
              <td>{client.defaultClientScopes}</td>
              <td>{client.clientAuthenticatorType}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  </>;
}