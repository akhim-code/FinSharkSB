import { ChangeEvent, SyntheticEvent, useState } from 'react';
import './App.css';
import CardList from './Components/CardList/CardList';
import Search from './Components/Search/Search';
import { searchCompanies } from './api';
import { CompanySearch } from './company';

function App() {
  const [search, setSearch] = useState<string>("");
  const [searchResult, setSearchResult] = useState<CompanySearch[]>([]);
  const [serverError, setServerError] = useState<string | null>(null);

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    setSearch(e.target.value);
  };

  const onClick = async (e: SyntheticEvent) => {
    const result = await searchCompanies(search);
    if (typeof result === "string") {
      setServerError(result);
      console.log(serverError);
    } 
    else if (Array.isArray(result.data)) {
      setSearchResult(result.data);
    }
  }
  
  return (
    <div className="App">
      <Search onClick={onClick} search={search} handleChange={handleChange} />
      <CardList searchResults={searchResult}/>
      {/* logical AND operator */}
      {serverError && <div>{serverError}</div>}                
      {/* ternary operator */}   
      {/* {serverError ? <div>Connected</div> : <div>Unable to connect to api</div>} */}    
    </div>
  );
}

export default App;
