package model.game;

import model.placeableObject.Weapon.Weapon;
import model.placeableObject.Weapon.WeaponFactory;
import model.placeableObject.Weapon.WeaponType;
import model.player.Player;

import java.util.ArrayList;

public class MoveData
{
    ArrayList<Integer> m_position;
    ArrayList<Boolean> m_isHit;
    ArrayList<Player> m_player;
    ArrayList<Weapon> m_weapon;
    public MoveData()
    {
        m_position = new ArrayList<>();
        m_isHit = new ArrayList<>();
        m_player = new ArrayList<>();
        m_weapon  = new ArrayList<>();
    }
    public void addData(int position, int hit, Player player, WeaponType weapon)
    {
        WeaponFactory weaponFactory = new WeaponFactory();
        m_position.add(position);
        m_isHit.add(false);
        m_player.add(player);
        m_weapon.add(weaponFactory.createWeapon(weapon));
    }
}