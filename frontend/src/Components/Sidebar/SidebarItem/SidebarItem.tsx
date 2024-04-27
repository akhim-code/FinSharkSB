import React from "react";
import * as Icons from "react-icons/fa";

const CustomFaIcon = ({ name }: { name: string }) => {
  const FaIcon = Icons[name as keyof typeof Icons];
  if (!FaIcon) return <p>Icon not found!</p>;

  return <FaIcon />;
};

interface Props {
  key: string;
  icon: string;
  title: string;
  onItemClicked: () => void;
  isActive: boolean;
}

const SidebarItem = ({ icon, title, onItemClicked }: Props) => {
  return (
    <p
      className="md:min-w-full text-blueGray-500 text-xs uppercase font-bold block pt-1 pb-4 no-underline"
      onClick={onItemClicked}
    >
      <CustomFaIcon name={icon}/>
      {title}
    </p>
  );
};

export default SidebarItem;