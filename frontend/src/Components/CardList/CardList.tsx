import React from 'react'
import Card from '../Card/Card'
import { v4 as uuidv4 } from 'uuid'
import { CompanySearch } from '../../company'
import { on } from 'events';

interface Props {
  searchResults: CompanySearch[];
  onPortfolioCreate: (e: React.SyntheticEvent) => void;
}

const CardList: React.FC<Props> = ({searchResults, onPortfolioCreate}: Props): JSX.Element => {
  return (
    <>
      {searchResults.length > 0 ? (
        searchResults.map((result) => {
          return (
            <Card
              id={result.symbol}
              key={uuidv4()}
              searchResult={result}
              onPortfolioCreate={onPortfolioCreate}
            />  
          );
        })
      ) : (
        <h1>No results found</h1>
      )}
    </>
  )
}

export default CardList