package com.solo.sololand;

import cn.nukkit.Server;
import cn.nukkit.Player;
import cn.nukkit.block.Block;

import cn.nukkit.event.Listener;
import cn.nukkit.event.Event;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.level.LevelInitEvent;
import cn.nukkit.event.server.ServerCommandEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockBurnEvent;
import cn.nukkit.event.player.PlayerLoginEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.event.player.PlayerDeathEvent;
import cn.nukkit.event.player.PlayerCommandPreprocessEvent;
import cn.nukkit.event.inventory.InventoryPickupItemEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityExplosionPrimeEvent;

import cn.nukkit.math.Vector3;

import cn.nukkit.level.Level;
import cn.nukkit.level.particle.Particle;
import cn.nukkit.level.particle.BubbleParticle;
import cn.nukkit.level.particle.CriticalParticle;
import cn.nukkit.level.particle.SmokeParticle;
import cn.nukkit.level.particle.HeartParticle;
import cn.nukkit.level.particle.LavaParticle;
import cn.nukkit.level.particle.FlameParticle;

import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.solo.sololand.Main;
import com.solo.sololand.world.World;
import com.solo.sololand.world.Island;
import com.solo.sololand.land.Land;
import com.solo.sololand.data.DataBase;
import com.solo.sololand.util.Message;
import com.solo.sololand.util.Queue;
import com.solo.sololand.util.Economy;
import com.solo.sololand.util.Debug;

public class EventListener implements Listener{

  private Main plugin;

  private HashMap<String, int[]> movePos = new HashMap<String, int[]>();
  private HashMap<String, Long> lastPick = new HashMap<String, Long>();

  public EventListener (Main plugin){
    this.plugin = plugin;
  }

  @EventHandler(
    ignoreCancelled = false,
    priority = EventPriority.MONITOR
  )
  public void onCmdPreprocess(PlayerCommandPreprocessEvent event){
    if(event.getMessage().startsWith("/")){
      if(Main.getInstance().getCommandMap().dispatch(event.getPlayer(), event.getMessage().substring(1))){
        event.setCancelled();
      }
    }
  }

  @EventHandler(
    ignoreCancelled = false,
    priority = EventPriority.MONITOR
  )
  public void onServerCommand(ServerCommandEvent event){
    if(Main.getInstance().getCommandMap().dispatch(event.getSender(), event.getCommand())){
      event.setCancelled();
    }
  }

  @EventHandler
  public void onLevelInit(LevelInitEvent event){
    Debug.normal("LevelInitEvent 발생");
    DataBase.loadWorld(event.getLevel());
  }

  @EventHandler
  public void onPickupItem(InventoryPickupItemEvent event){
    if(!(event.getInventory().getHolder() instanceof Player)){
      return;
    }
    Player player = (Player) event.getInventory().getHolder();
    String name = player.getName().toLowerCase();
    if(
      this.lastPick.containsKey(name) &&
      this.lastPick.get(name) + 2000 > System.currentTimeMillis()
    ){
      event.setCancelled();
      return;
    }
    World world = World.get(player.getLevel());
    Land land = world.getLand(event.getItem().getFloorX(), event.getItem().getFloorZ());
    if(land == null){
      return;
    }
    if(land.isAllowPickUpItem()){
      return;
    }
    if(land.isMember(name)){
      return;
    }
    this.lastPick.put(name, System.currentTimeMillis());
    event.setCancelled();
    Message.alert(player, "아이템을 주울 수 없습니다");
  }

  @EventHandler
  public void onMove(PlayerMoveEvent event){
    Player player = event.getPlayer();
    String name = player.getName();
 
    int[] pos = new int[4];
    pos[0] = player.getFloorX();
    pos[1] = player.getFloorY();
    pos[2] = player.getFloorZ();

    if(!this.movePos.containsKey(name)){
      pos[3] = 0;
      this.movePos.put(name, pos);
    }else{
      int[] oldPos = this.movePos.get(name);
      if(
        Math.abs(oldPos[0] - pos[0]) < 1 &&
        Math.abs(oldPos[1] - pos[1]) < 1 &&
        Math.abs(oldPos[2] - pos[2]) < 1
      ){
        return;
      }
      World world = World.get(player.getLevel());
      Land land = world.getLand(pos[0], pos[2], player.getName());
      if(land != null){
        int num = land.getNumber();
        if(num != oldPos[3]){
          pos[3] = num;

          particleProcess : {
            if(
              !player.isOp() &&
              !land.isMember(name) &&
              !land.isAllowAccess()
            ){
              break particleProcess;
            }
            Particle particle;
            Vector3 vec = new Vector3(pos[0] + 0.5, pos[1]+1, pos[2] + 0.5);
            switch(land.getWelcomeParticle()){
              case Particle.TYPE_BUBBLE:
                particle = new BubbleParticle(vec);
                break;
              case Particle.TYPE_CRITICAL:
                particle = new CriticalParticle(vec);
                break;
              case Particle.TYPE_SMOKE:
                particle = new SmokeParticle(vec);
                break;
              case Particle.TYPE_HEART:
                particle = new HeartParticle(vec);
                break;
              case Particle.TYPE_LAVA:
                particle = new LavaParticle(vec);
                break;
              case Particle.TYPE_FLAME:
                particle = new FlameParticle(vec);
                break;
              case 0:
              default:
                break particleProcess;
            }
            Random random = new Random(System.currentTimeMillis());
            for (int i = 0; i < 10; i++){
              particle.setComponents(
                vec.x + (random.nextFloat() * 2 - 1) * 0.5,
                vec.y + (random.nextFloat() * 2 - 1) * 1,
                vec.z + (random.nextFloat() * 2 - 1) * 0.5
              );
              player.getLevel().addParticle(particle);
            }
          }

          if(land.isOwner(name)){
            Message.tip(player, "[" + Integer.toString(num) + "번 땅] 본인의 땅입니다\n" + land.getWelcomeMessage());
          }else if(land.isOwner("")){ 
            Message.tip(player, "[" + Integer.toString(num) + "번 땅] 주인이 없습니다\n" + land.getWelcomeMessage());
          }else{
            Message.tip(player, "[" + Integer.toString(num) + "번 땅] " + land.getOwner() + "님의 땅입니다\n" + land.getWelcomeMessage());
            Player owner = Server.getInstance().getPlayerExact(land.getOwner());
            if(owner != null){
              Message.tip(owner, "[" + Integer.toString(num) + "번 땅] " + player.getName() + "님이 방문하셨습니다");
            }
          }
          if(land.isSail()){
            Message.popup(player, "현재 " + Double.toString(land.getPrice()) + "원에 판매중입니다");
          } 
        }else{
          pos[3] = oldPos[3];
        }
        if(
          !player.isOp() &&
          !land.isMember(name) &&
          !land.isAllowAccess()
        ){
          Land oldPosLand = world.getLand(oldPos[0], oldPos[2], player.getName());
          if(oldPosLand != null && oldPosLand.getNumber() == oldPos[3]){
            int y = player.getLevel().getHighestBlockAt(land.startX - 1, land.startZ - 1) + 1;
            player.teleport(new Vector3(land.startX - 0.5, y, land.startZ - 0.5));
          }else{
            player.teleport(new Vector3(oldPos[0] + 0.5, oldPos[1], oldPos[2] + 0.5));
          }
          Message.alert(player, "출입이 허용되지 않은 땅입니다");
          return;
        }
      }else if(pos[3] != oldPos[3]){
        Message.popup(player, Integer.toString(oldPos[3]) + "번 땅에서 나갔습니다");
      }
      this.movePos.put(name, pos);
    }
  }


  @EventHandler
  public void onLogin(PlayerLoginEvent event){
    Queue.clean(event.getPlayer().getName().toLowerCase());
  }
  @EventHandler
  public void onQuit(PlayerQuitEvent event){
    Queue.clean(event.getPlayer().getName().toLowerCase());
  }


  @EventHandler
  public void onExplosion(EntityExplosionPrimeEvent event){
    World world = World.get(event.getEntity().getLevel());
    if(!world.isAllowExplosion()){
      event.setBlockBreaking(false);
    }
  }

  @EventHandler
  public void onBurn(BlockBurnEvent event){
    World world = World.get(event.getBlock().getLevel());
    if(!world.isAllowBurn()){
      event.setCancelled();
    }
  }

  @EventHandler
  public void onDamage(EntityDamageEvent event){
    if(event instanceof EntityDamageByEntityEvent){
      EntityDamageByEntityEvent ev = (EntityDamageByEntityEvent) event;
      if(
        ev.getEntity() instanceof Player &&
        ev.getDamager() instanceof Player
      ){
        World world = World.get(ev.getEntity().getLevel());
        Player player = (Player) ev.getEntity();
        Land land = world.getLand(player.getFloorX(), player.getFloorZ(), player.getName());

        if(land != null && land.isAllowFight()){
          return;
        }else if(world.isAllowFight()){
          return;
        }
        event.setCancelled();
      }
    }
  }

  @EventHandler
  public void onDeath(PlayerDeathEvent event){
    if(event.getEntity() instanceof Player){
      if(World.get(event.getEntity().getLevel()).isInvenSave()){
        event.setKeepInventory(true);
        event.setKeepExperience(true);
      }
    }
  }

  public void onBlockModify(Event event, Player player, Block block) {
    if(player.isOp()){
      return;
    }
    World world = World.get(block.getLevel());
    String name = player.getName().toLowerCase();
    Land land = world.getLand((int)block.x, (int)block.z, player.getName());
    if(land != null){
      if(!land.isMember(name)){
        Message.alert(player, "§c이 땅을 수정할 수 없습니다");
        event.setCancelled();
      }
    }else if(world.isProtected()){
      event.setCancelled();
    }
  }

  @EventHandler
  public void onBreak(BlockBreakEvent event){
    this.onBlockModify(event, event.getPlayer(), event.getBlock());
  }

  @EventHandler
  public void onPlace(BlockPlaceEvent event){
    this.onBlockModify(event, event.getPlayer(), event.getBlock());
  }

  @EventHandler
  public void onInteract(PlayerInteractEvent event){

    Player player = event.getPlayer();
    String name = player.getName().toLowerCase();

    this.onBlockModify(event, player, event.getBlock());

    if(event.getAction() == PlayerInteractEvent.RIGHT_CLICK_BLOCK){
      if(!Queue.containsQueue(name)){
        return;
      }
      if(Queue.getQueue(name) == Queue.NULL){
        return;
      }

      World world = World.get(player.getLevel());
      Land overlap;
      int[] xz = new int[2];
      xz[0] = (int) event.getBlock().x;
      xz[1] = (int) event.getBlock().z;
      HashMap<String, Object> map = new HashMap<String, Object>();

      int Xlength;
      int Zlength; 
      double myMoney = Economy.getMoney(name);
      double price;

      queueProcess : {
        switch(Queue.getQueue(name)){
          case Queue.CREATE_LAND_FIRST:
            map.put("first", xz);
            Message.normal(player, "첫번째 지점을 선택하였습니다. 두번째 지점을 선택해주세요.");
            Queue.putQueue(name, Queue.CREATE_LAND_SECOND);
            break;

          case Queue.CREATE_LAND_SECOND:
            map = (HashMap<String, Object>) Queue.getData(name);
            map.put("second", xz);

            int[] first = (int[]) map.get("first");
            int startX = Math.min(first[0], xz[0]);
            int startZ = Math.min(first[1], xz[1]);
            int endX = Math.max(first[0], xz[0]);
            int endZ = Math.max(first[1], xz[1]);
            Xlength = endX - startX + 1;
            Zlength = endZ - startZ + 1;

            Message.normal(player, "두번째 지점을 선택하였습니다.");
            overlap = world.checkOverlap(startX, startZ, endX, endZ);
            if(overlap != null){
              Message.fail(player, Integer.toString(overlap.getNumber()) + "번 땅과 겹칩니다. 땅 생성을 취소합니다.");
              Queue.clean(name);
              return;
            }
            Message.normal(player, "땅 가로 길이 : " + Integer.toString(Xlength) + ", 땅 세로 길이 : " + Integer.toString(Zlength));
            if(
              world.getMinLandLength() > Xlength ||
              world.getMinLandLength() > Zlength
            ){
              Message.fail(player, "땅의 한변 길이는 최소 " + Integer.toString(world.getMinLandLength()) + "블럭 이어야 합니다. 땅 생성을 취소합니다.");
              Queue.clean(name);
              return;
            }
            if(
              world.getMaxLandLength() < Xlength ||
              world.getMaxLandLength() < Zlength
            ){
              Message.fail(player, "땅의 한변 길이는 " + Integer.toString(world.getMaxLandLength()) + "블럭을 넘을 수 없습니다. 땅 생성을 취소합니다.");
              Queue.clean(name);
              return;
            }
            if(!player.isOp()){
              price = world.getPricePerBlock() * Xlength * Zlength;
              Message.normal(player, "땅 생성 비용은 " + Double.toString(price) + "원 입니다.");
              if(myMoney < price) {
                Message.fail(player, "돈이 부족합니다. 현재 소지한 돈 : " + Double.toString(myMoney) + "원");
                Queue.clean(name);
                return;
              }
            }
            Message.normal(player, "땅을 생성하려면 /땅 생성 명령어를 다시 입력해주세요. 취소하려면 /땅 취소 명령어를 입력해주세요.");
            Queue.putQueue(name, Queue.CREATE_LAND_THIRD);
            break;

          case Queue.RESIZE_LAND_FIRST:
            map = (HashMap<String, Object>) Queue.getData(name);
            Land land = (Land) map.get("land");
            if(!player.isOp() && !land.isOwner(name)){
              Message.fail(player, "땅 주인이 아니므로 땅 크기변경을 할 수 없습니다.");
              Queue.clean(name);
              return;
            }
            map.put("xz", xz);

            int newStartX; int newEndX;
            int newStartZ; int newEndZ;
            int startXDist = Math.abs(land.startX - xz[0]);
            int endXDist = Math.abs(land.endX - xz[0]);
            int startZDist = Math.abs(land.startZ - xz[1]);
            int endZDist = Math.abs(land.endZ - xz[1]);
            if(startXDist < endXDist){
              newStartX = xz[0];
              newEndX = land.endX;
            }else{
              newStartX = land.startX;
              newEndX = xz[0];
            }
            if(startZDist < endZDist){
              newStartZ = xz[1];
              newEndZ = land.endZ;
            }else{
              newStartZ = land.startZ;
              newEndZ = xz[1];
            }

            for(Land chk : world.lands.values()){
              if(
                land.endX < newStartX ||
                land.startX > newEndX ||
                land.endZ < newStartZ ||
                land.startZ > newEndZ ||
                land.getNumber() == chk.getNumber()
              ){
                continue;
              }
              Message.fail(player, Integer.toString(chk.getNumber()) + "번 땅과 겹칩니다. 땅 크기변경을 취소합니다.");
              Queue.clean(name);
              return;
            }

            Xlength = Math.abs(newStartX - newEndX) + 1;
            Zlength = Math.abs(newStartZ - newEndZ) + 1;
            Message.normal(player, "땅 가로 길이 : " + Integer.toString(Xlength) + ", 땅 세로 길이 : " + Integer.toString(Zlength));

            if(world.getMinLandLength() > Xlength || world.getMinLandLength() > Zlength){
              Message.fail(player, "땅의 한변 길이는 최소 " + Integer.toString(world.getMinLandLength()) + "블럭 이어야 합니다. 땅 생성을 취소합니다.");
              Queue.clean(name);
              return;
            }

            if(world.getMaxLandLength() < Xlength || world.getMaxLandLength() < Zlength){
              Message.fail(player, "땅의 한변 길이는 " + Integer.toString(world.getMaxLandLength()) + "블럭을 넘을 수 없습니다. 땅 생성을 취소합니다.");
              Queue.clean(name);
              return;
            }

            int oldSize = (Math.abs(land.startX - land.endX) + 1) * (Math.abs(land.startZ - land.endZ) + 1);
            int newSize = Xlength * Zlength;
            if(!player.isOp() && newSize > oldSize){
              price = world.getPricePerBlock() * (newSize - oldSize);
              Message.normal(player, "땅 크기변경 비용은 " + Double.toString(price) + "원 입니다.");
              if(myMoney < price){
                Message.fail(player, "돈이 부족합니다. 현재 소지한 돈 : " + Double.toString(myMoney) + "원, 크기변경 시 필요한 돈 : " + Double.toString(price) + "원");
                Queue.clean(name);
                return;
              }
            }
            Message.normal(player, "땅을 크기변경하려면 /땅 크기변경 명령어를 다시 입력해주세요. 취소하려면 /땅 취소 명령어를 입력해주세요.");
            Queue.putQueue(name, Queue.RESIZE_LAND_SECOND);
            break;

          default:
            break queueProcess;
        } 
        Queue.putData(name, map);
        event.setCancelled();
      }//QueueProcess
    }
  }


}