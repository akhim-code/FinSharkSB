import { ChangeEvent, SyntheticEvent, useState } from 'react';
import './App.css';
import CardList from './Components/CardList/CardList';
import Search from './Components/Search/Search';
import { searchCompanies } from './api';
import { CompanySearch } from './company';
import ListPortfolio from './Components/ListPortfolio/ListPortfolio';

function App() {
  const [search, setSearch] = useState<string>("");
  const [searchResult, setSearchResult] = useState<CompanySearch[]>([]);
  const [serverError, setServerError] = useState<string | null>(null);
  const [portfolioValues, setPortfolioValues] = useState<string[]>([]);

  const handleSearchChange = (e: ChangeEvent<HTMLInputElement>) => {
    setSearch(e.target.value);
  };

  const onPortfolioCreate = (e : any) => {
    e.preventDefault();
    const exists = portfolioValues.find((value) => value === e.target[0].value);
    if (exists) {
      return;
    }
    const updatedPortfolio = [...portfolioValues, e.target[0].value];
    setPortfolioValues(updatedPortfolio);
  }

  const onSearchSubmit = async (e: SyntheticEvent) => {
    e.preventDefault();
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
      <Search onSearchSubmit={onSearchSubmit} search={search} handleSearchChange={handleSearchChange} />
      <ListPortfolio portfolioValues={portfolioValues} />
      <CardList searchResults={searchResult} onPortfolioCreate={onPortfolioCreate}/>
      {/* logical AND operator */}
      {serverError && <div>{serverError}</div>}                
      {/* ternary operator */}   
      {/* {serverError ? <div>Connected</div> : <div>Unable to connect to api</div>} */}    
    </div>
  );
}

export default App;
