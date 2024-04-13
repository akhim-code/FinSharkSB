import React, { ChangeEvent, SyntheticEvent } from 'react'

type Props = {}

const Search: React.FC<Props> = (props: Props): JSX.Element => {
    const [search, setSearch] = React.useState<string>('')

    const handleChange = (e:ChangeEvent<HTMLInputElement>) => {
        setSearch(e.target.value)
        console.log(search)
    }

    const onClick = (e: SyntheticEvent) => {
        console.log(search);
    };

  return (
    <div>
        <input value={search} onChange={(e)=>handleChange(e)}></input>
        <button onClick={(e)=>onClick(e)}>Search</button>
    </div>
  )
}

export default Search