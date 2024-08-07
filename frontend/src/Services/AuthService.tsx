import axios from "axios";
import { handleError } from "../Helpers/ErrorHandler";
import { UserProfileToken } from "../Models/User";

const api = "http://localhost:8080/api/";

export const loginAPI = async (username: string, password: string) => {
  try {
    const passwordArray = Array.from(password);
    const data = await axios.post<UserProfileToken>(api + "auth/login", {
      username: username,
      password: passwordArray,
    });
    return data;
  } catch (error) {
    handleError(error);
  }
};

export const registerAPI = async (
  email: string,
  username: string,
  password: string
) => {
  try {
    const data = await axios.post<UserProfileToken>(api + "auth/register", {
      email: email,
      username: username,
      password: password,
    });
    return data;
  } catch (error) {
    handleError(error);
  }
};