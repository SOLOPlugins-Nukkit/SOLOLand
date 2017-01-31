package solo.sololand;

import cn.nukkit.Server;
import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockChest;
import cn.nukkit.block.BlockDoor;
import cn.nukkit.block.BlockEnderChest;
import cn.nukkit.block.BlockWorkbench;
import cn.nukkit.event.Listener;
import cn.nukkit.event.Event;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.level.LevelInitEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockBurnEvent;
import cn.nukkit.event.player.PlayerLoginEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.event.player.PlayerDeathEvent;
import cn.nukkit.event.inventory.InventoryPickupItemEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityExplosionPrimeEvent;
import cn.nukkit.event.entity.EntityLevelChangeEvent;
import cn.nukkit.math.Vector3;
import cn.nukkit.level.particle.Particle;
import cn.nukkit.level.Location;
import cn.nukkit.level.Position;
import cn.nukkit.level.particle.GenericParticle;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Random;

import solo.sololand.Main;
import solo.sololand.event.land.LandEnterEvent;
import solo.sololand.event.land.LandLeaveEvent;
import solo.sololand.event.player.PlayerFloorMoveEvent;
import solo.sololand.event.room.RoomEnterEvent;
import solo.sololand.event.room.RoomLeaveEvent;
import solo.sololand.world.World;
import solo.sololand.land.Land;
import solo.sololand.land.Room;
import solo.sololand.external.Message;
import solo.sololand.queue.Queue;
import solo.sololand.external.Economy;
import solo.sololand.external.Debug;

public class EventListener implements Listener{

	private Map<String, Position> movePos = new HashMap<String, Position>();
	private Map<String, Land> currentLandList = new HashMap<String, Land>();
	private Map<String, Room> currentRoomList = new HashMap<String, Room>();
	private Map<String, Long> lastPick = new HashMap<String, Long>();

	@EventHandler
	public void onLevelInit(LevelInitEvent event){
		Debug.normal(Main.getInstance(), "LevelInitEvent 발생");
		World.load(event.getLevel());
	}
	
	//@EventHandler
	//public void onCommandPreprocess(PlayerCommandPreprocessEvent event){
	//	Debug.normal(Main.getInstance(), event.getPlayer().getName() + " : " + event.getMessage());
	//}

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
		World world = World.get(player);
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
		Room room = land.getRoom(event.getItem());
		if(room != null&& (room.isOwner(player) || room.isMember(player))){
			return;
		}
		this.lastPick.put(name, System.currentTimeMillis());
		event.setCancelled();
		Message.alert(player, "아이템을 주울 수 없습니다");
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent event){
		Player player = event.getPlayer();
		String name = player.getName().toLowerCase();
		
		Position currentPos = new Position(player.getFloorX(), player.getFloorY(), player.getFloorZ());
		
		if(this.movePos.containsKey(name)){
			Position oldPos = this.movePos.get(name);
			if(currentPos.x != oldPos.x || currentPos.y != oldPos.y || currentPos.z != oldPos.z){
				PlayerFloorMoveEvent ev = new PlayerFloorMoveEvent(player, oldPos, currentPos);
				Server.getInstance().getPluginManager().callEvent(ev);
				if(! ev.isCancelled()){
					this.movePos.put(name, currentPos);
				}else{
					event.setTo(new Location(oldPos.x + 0.5, oldPos.y, oldPos.z + 0.5, event.getTo().getYaw(), event.getTo().getPitch(), event.getTo().getLevel()));
				}
			}
		}else{
			this.movePos.put(name, currentPos);
		}
	}
	
	@EventHandler
	public void onFloorMove(PlayerFloorMoveEvent event){
		Player player = event.getPlayer();
		String name = player.getName().toLowerCase();
		World world = World.get(player);
		Land land = world.getLand(event.getTo());
		Land currentLand = null;
		
		if(this.currentLandList.containsKey(name)){
			currentLand = this.currentLandList.get(name);
		}

		//land process START
		if(land != null){
			
			//room process START
			Room room = land.getRoom(event.getTo());
			Room currentRoom = this.currentRoomList.get(name);
			if(room != null){
				if(currentRoom == null || currentRoom != room){
					
					//room welcomeMessage part
					String welcomeRoomMessage = "[" + Integer.toString(room.getNumber()) + "번 방] ";
					if(room.isOwner("")){
						welcomeRoomMessage += "주인이 없습니다";
					}else if(room.isOwner(player)){
						welcomeRoomMessage += "본인의 방입니다";
					}else if(room.isMember(player)){
						welcomeRoomMessage += room.getOwner() + "님으로 부터 공유받은 방입니다";
					}else{
						welcomeRoomMessage += room.getOwner() + "님의 방입니다";
					}
					if(! room.getWelcomeMessage().equals("")){
						welcomeRoomMessage += "\n" + room.getWelcomeMessage();
					}
					if(room.isSail()){
						welcomeRoomMessage += "\n현재 " + Double.toString(room.getPrice()) + "원에 판매중입니다";
					}
					
					//call RoomEnterEvent
					RoomEnterEvent roomEnterEv = new RoomEnterEvent(player, room, welcomeRoomMessage, Message.TYPE_TIP);
					Server.getInstance().getPluginManager().callEvent(roomEnterEv);
					if(! roomEnterEv.isCancelled()){
						Message.normal(player, roomEnterEv.getWelcomeMessage(), roomEnterEv.getMessageType());
						this.currentRoomList.put(name, room);
					}else{
						event.setCancelled();
					}
				}
			}else if(currentRoom != null){
				RoomLeaveEvent roomLeaveEv = new RoomLeaveEvent(player, currentRoom, Integer.toString(currentRoom.getNumber()) + "번 방에서 나갔습니다", Message.TYPE_TIP);
				Server.getInstance().getPluginManager().callEvent(roomLeaveEv);
				if(! roomLeaveEv.isCancelled()){
					Message.normal(player, roomLeaveEv.getLeaveMessage(), roomLeaveEv.getMessageType());
					this.currentRoomList.remove(name);
				}else{
					event.setCancelled();
				}
			}
			//room process END
			
			
			
			if(currentLand == null || currentLand != land){
			
				//welcomeMessage part
				String welcomeMessage = "[" + Integer.toString(land.getNumber()) + "번 땅] ";
				if(land.isOwner("")){
					welcomeMessage += "주인이 없습니다";
				}else if(land.isOwner(name)){
					welcomeMessage += "본인의 땅입니다";
				}else if(land.isMember(name)){
					welcomeMessage += land.getOwner() + "님으로 부터 공유받은 땅입니다";
				}else{
					welcomeMessage += land.getOwner() + "님의 땅입니다";
				}
				if(! land.getWelcomeMessage().equals("")){
					welcomeMessage += "\n" + land.getWelcomeMessage();
				}
				if(land.isSail()){
					welcomeMessage += "\n현재 " + Double.toString(land.getPrice()) + "원에 판매중입니다";
				}
				
				//call LandEnterEvent
				LandEnterEvent landEnterEv = new LandEnterEvent(player, land, welcomeMessage, land.getWelcomeParticle(), Message.TYPE_TIP);
				Server.getInstance().getPluginManager().callEvent(landEnterEv);
				
				if(! landEnterEv.isCancelled()){
					Message.normal(player, landEnterEv.getWelcomeMessage(), landEnterEv.getMessageType());
							
					//particle process
					int particleId = landEnterEv.getWelcomeParticle();
					if(particleId > 0 || particleId < 41){
						Vector3 vec = new Vector3(event.getTo().x + 0.5, event.getTo().y + 1, event.getTo().z + 0.5);
						Particle particle = new GenericParticle(vec, particleId);
						Random random = new Random(System.currentTimeMillis());
						for(int i = 0; i < 10; i++){
							particle.setComponents(
								vec.x + (random.nextFloat() * 2 - 1) * 0.5,
								vec.y + (random.nextFloat() * 2 - 1) * 1,
								vec.z + (random.nextFloat() * 2 - 1) * 0.5
							);
							player.getLevel().addParticle(particle);
						}
					}
					this.currentLandList.put(name, land);
				}else{
					event.setCancelled();
				}
			}
		}else if(currentLand != null){
			LandLeaveEvent landLeaveEv = new LandLeaveEvent(player, currentLand, Integer.toString(currentLand.getNumber()) + "번 땅에서 나갔습니다", Message.TYPE_TIP);
			Server.getInstance().getPluginManager().callEvent(landLeaveEv);
			if(! landLeaveEv.isCancelled()){
				Message.normal(player, landLeaveEv.getLeaveMessage(), landLeaveEv.getMessageType());
				this.currentLandList.remove(name);
			}else{
				event.setCancelled();
			}
		}
		//land process END
	}
	
	@EventHandler
	public void onLandEnter(LandEnterEvent event){
		Land land = event.getLand();
		if(
			! land.isAllowAccess() &&
			! event.getPlayer().isOp() &&
			! land.isOwner(event.getPlayer()) &&
			! land.isMember(event.getPlayer())
		){
			for(Room room : land.getRooms().values()){
				if(
					! room.isOwner(event.getPlayer()) &&
					! room.isMember(event.getPlayer())
				){
					Message.alert(event.getPlayer(), "출입이 허용되지 않은 땅입니다", Message.TYPE_POPUP);
					event.setCancelled();
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
	public void onLogin(PlayerLoginEvent event){
		Queue.clean(event.getPlayer());
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
	public void onQuit(PlayerQuitEvent event){
		Queue.clean(event.getPlayer());
		
		String name = event.getPlayer().getName().toLowerCase();
		this.movePos.remove(name);
		this.currentLandList.remove(name);
		this.currentRoomList.remove(name);
		this.lastPick.remove(name);
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
	public void onLevelChange(EntityLevelChangeEvent event){
		if(event.getEntity() instanceof Player){
			Player player = (Player) event.getEntity();
			if(Queue.get(player) != Queue.NULL){
				Message.alert(player, "다른 월드로 이동하여, 진행중이던 작업이 취소되었습니다.");
			}
			Queue.clean(player);
			
			//String name = player.getName().toLowerCase();
			//this.movePos.remove(name);
			//this.currentLandList.remove(name);
			//this.currentRoomList.remove(name);
			//this.lastPick.remove(name);
		}
	}


	@EventHandler
	public void onExplosion(EntityExplosionPrimeEvent event){
		if(!World.get(event.getEntity().getLevel()).isAllowExplosion()){
			event.setBlockBreaking(false);
		}
	}

	@EventHandler
	public void onBurn(BlockBurnEvent event){
		if(!World.get(event.getBlock().getLevel()).isAllowBurn()){
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
				Land land = world.getLand(player.getFloorX(), player.getFloorZ());

				if(land != null && !land.isAllowFight()){
					event.setCancelled();
				}else if(! world.isAllowFight()){
					event.setCancelled();
				}
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
		Land land = world.getLand(block.getFloorX(), block.getFloorZ());
		if(land != null){
			
			//check if land allow stuff such as door, chest, workbench
			if(event instanceof PlayerInteractEvent){
				PlayerInteractEvent ev = (PlayerInteractEvent) event;
				if(land.isAllowChest() && (ev.getBlock() instanceof BlockChest || ev.getBlock() instanceof BlockEnderChest)){
					return;
				}else if(land.isAllowDoor() && ev.getBlock() instanceof BlockDoor){
					return;
				}//else if(land.isAllowCraft() && ev.getBlock() instanceof BlockWorkbench){
				//	return;
				//}
			}
			
			//check room
			if(land.hasRoom()){
				Room room = land.getRoom(block);
				if(room != null){
					if(!land.isOwner(player) && !room.isOwner(player) && !room.isMember(player)){
						Message.alert(player, "이 방을 수정할 수 없습니다", Message.TYPE_POPUP);
						event.setCancelled();
					}else if(room.isSail()){
						Message.alert(player, "방이 매물에 등록되어 있는 동안엔 방을 수정할 수 없습니다", Message.TYPE_POPUP);
						event.setCancelled();
					}else if(land.isSail()){
						Message.alert(player, "땅이 매물에 등록되어 있는 동안엔 땅을 수정할 수 없습니다", Message.TYPE_POPUP);
						event.setCancelled();
					}
					return;
				}
			}
			if(!land.isOwner(player) && !land.isMember(player)){
				if(! (event instanceof PlayerInteractEvent)){
					Message.alert(player, "이 땅을 수정할 수 없습니다", Message.TYPE_POPUP);
				}
				event.setCancelled();
			}else if(land.isSail()){
				Message.alert(player, "땅이 매물에 등록되어 있는 동안엔 땅을 수정할 수 없습니다", Message.TYPE_POPUP);
				event.setCancelled();
			}
			return;
		}
		//check if world allow stuff such as door, chest, workbench
		if(event instanceof PlayerInteractEvent){
			PlayerInteractEvent ev = (PlayerInteractEvent) event;
			if(world.isAllowChest() && (ev.getBlock() instanceof BlockChest || ev.getBlock() instanceof BlockEnderChest)){
				return;
			}else if(world.isAllowDoor() && ev.getBlock() instanceof BlockDoor){
				return;
			}//else if(world.isAllowCraft() && ev.getBlock() instanceof BlockWorkbench){
			//	return;
			//}
		}
		if(world.isProtected()){
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

		this.onBlockModify(event, player, event.getBlock());

		if(event.getAction() == PlayerInteractEvent.RIGHT_CLICK_BLOCK){
			if(Queue.get(player) == Queue.NULL){
				return;
			}
			String name = player.getName().toLowerCase();

			World world = World.get(player);
			Land land;
			Land afterLand;
			ArrayList<Land> overlapList;
			StringBuilder sb;
			boolean f;

			double myMoney = Economy.getMoney(name);
			double price;

			queueProcess : {
				switch(Queue.get(player)){
					case Queue.LAND_CREATE_FIRST:
						Message.normal(player, "첫번째 지점을 선택하였습니다. 두번째 지점을 선택해주세요.");
						Queue.setPosition(player, event.getBlock());
						Queue.set(player, Queue.LAND_CREATE_SECOND);
						break;

					case Queue.LAND_CREATE_SECOND:
						land = world.createLand(Queue.getPosition(player), event.getBlock(), event.getPlayer().getName());
						Queue.setPosition(player, null);
						Queue.setTemporaryLand(player, land);
						Message.normal(player, "두번째 지점을 선택하였습니다.");
						
						overlapList = world.getOverlap(land);
						if(overlapList.size() > 0){
							sb = new StringBuilder();
							f = true;
							for(Land overlap : overlapList){
								if(f){
									f = false;
								}else{
									sb.append(", ");
								}
								sb.append(Integer.toString(overlap.getNumber()) + "번");
							}
							Message.alert(player, sb.toString()+ " 땅과 겹칩니다. 땅 생성을 취소합니다.");
							Queue.clean(player);
							break;
						}
						Message.normal(player, "땅 크기 : " + land.xLength() + "x" + land.zLength() + " (" + land.size() + "블럭)");
						if(
							world.getMinLandLength() > land.xLength() ||
							world.getMinLandLength() > land.zLength()
						){
							Message.alert(player, "땅의 한변 길이는 최소 " + Integer.toString(world.getMinLandLength()) + "블럭 이어야 합니다. 땅 생성을 취소합니다.");
							Queue.clean(player);
							break;
						}
						if(
							world.getMaxLandLength() < land.xLength() ||
							world.getMaxLandLength() < land.zLength()
						){
							Message.alert(player, "땅의 한변 길이는 " + Integer.toString(world.getMaxLandLength()) + "블럭을 넘을 수 없습니다. 땅 생성을 취소합니다.");
							Queue.clean(player);
							break;
						}
						if(!player.isOp()){
							price = world.getPricePerBlock() * land.xLength() * land.zLength();
							Message.normal(player, "땅 생성 비용은 " + Double.toString(price) + "원 입니다.");
							if(myMoney < price) {
								Message.alert(player, "돈이 부족합니다. 현재 소지한 돈 : " + Double.toString(myMoney) + "원");
								Queue.clean(player);
								break;
							}
						}
						Message.normal(player, "땅을 생성하려면 /땅 생성 명령어를 다시 입력해주세요. 취소하려면 /땅 취소 명령어를 입력해주세요.");
						Queue.set(player, Queue.LAND_CREATE_THIRD);
						break;

					case Queue.LAND_EXPAND_FIRST:
						land = Queue.getLand(player);
						if(!player.isOp() && !land.isOwner(name)){
							Message.alert(player, "땅 주인이 아니므로 땅 크기변경을 할 수 없습니다.");
							Queue.clean(player);
							break;
						}
						if(land.isInside(event.getBlock())){
							Message.alert(player, "땅 바깥을 터치하여야 땅 확장이 가능합니다.");
							Message.alert(player, "땅 확장을 중지합니다.");
							Queue.clean(player);
							break;
						}
						afterLand = land.clone();
						afterLand.expand(event.getBlock());

						overlapList = world.getOverlap(afterLand);
						if(overlapList.size() > 0){
							sb = new StringBuilder();
							f = true;
							for(Land overlap : overlapList){
								if(f){
									f = false;
								}else{
									sb.append(", ");
								}
								sb.append(Integer.toString(overlap.getNumber()) + "번");
							}
							Message.alert(player, sb.toString() + " 땅과 겹칩니다. 땅 확장을 취소합니다.");
							Queue.clean(player);
							break;
						}

						Message.normal(player, "변경 전 : " + land.xLength() + "x" + land.zLength() + " (" + land.size() + "블럭)");
						Message.normal(player, "변경 후 : " + afterLand.xLength() + "x" + afterLand.zLength() + " (" + afterLand.size() + "블럭)");

						if(world.getMaxLandLength() < afterLand.xLength() || world.getMaxLandLength() < afterLand.zLength()){
							Message.alert(player, "땅의 한변 길이는 " + Integer.toString(world.getMaxLandLength()) + "블럭을 넘을 수 없습니다. 땅 확장을 취소합니다.");
							Queue.clean(player);
							break;
						}
						
						if(!player.isOp()){
							price = world.getPricePerBlock() * (afterLand.size() - land.size());
							Message.normal(player, "땅 확장 비용은 " + Double.toString(price) + "원 입니다.");
							if(myMoney < price){
								Message.alert(player, "돈이 부족합니다. 현재 소지한 돈 : " + Double.toString(myMoney) + "원");
								Queue.clean(player);
								break;
							}
						}
						Message.normal(player, "땅을 확장하려면 /땅 확장 명령어를 입력해주세요. 취소하려면 /땅 취소 명령어를 입력해주세요.");
						Queue.setTemporaryLand(player, afterLand);
						Queue.set(player, Queue.LAND_EXPAND_SECOND);
						break;
						
					case Queue.LAND_REDUCE_FIRST:
						land = Queue.getLand(player);
						if(!player.isOp() && !land.isOwner(name)){
							Message.alert(player, "땅 주인이 아니므로 땅 크기변경을 할 수 없습니다.");
							Queue.clean(player);
							break;
						}
						if(! land.isInside(event.getBlock())){
							Message.alert(player, "땅 안쪽을 터치하여야 땅 축소가 가능합니다.");
							Message.alert(player, "땅 축소를 중지합니다.");
							Queue.clean(player);
							break;
						}
						afterLand = land.clone();
						afterLand.reduce(event.getBlock());

						/*
						### NEED NOT TO CHECK OVERLAP ###
						
						overlapList = world.getOverlap(afterLand);
						if(overlapList.size() > 0){
							sb = new StringBuilder();
							f = true;
							for(Land overlap : overlapList){
								if(f){
									f = false;
								}else{
									sb.append(", ");
								}
								sb.append(Integer.toString(overlap.getNumber()) + "번");
							}
							Message.alert(player, sb.toString() + "번 땅과 겹칩니다. 땅 확장을 취소합니다.");
							Queue.clean(player);
							break;
						}
						*/

						Message.normal(player, "변경 전 : " + land.xLength() + "x" + land.zLength() + " (" + land.size() + "블럭)");
						Message.normal(player, "변경 후 : " + afterLand.xLength() + "x" + afterLand.zLength() + " (" + afterLand.size() + "블럭)");

						if(world.getMinLandLength() > afterLand.xLength() || world.getMinLandLength() > afterLand.zLength()){
							Message.alert(player, "땅의 한변 길이는 최소 " + Integer.toString(world.getMinLandLength()) + "블럭 이어야 합니다. 땅 축소를 취소합니다.");
							Queue.clean(player);
							break;
						}
						Message.normal(player, "땅을 축소하려면 /땅 축소 명령어를 입력해주세요. 취소하려면 /땅 취소 명령어를 입력해주세요.");
						Queue.setTemporaryLand(player, afterLand);
						Queue.set(player, Queue.LAND_REDUCE_SECOND);
						break;

					case Queue.ROOM_CREATE_FIRST:
						land = Queue.getLand(player);
						if(!land.isInside(event.getBlock())){
							Message.normal(player, "땅 안쪽을 터치해주세요.");
							break;
						}
						Message.normal(player, "첫번째 지점을 선택하였습니다. 두번째 지점을 선택해주세요.");
						Queue.setPosition(player, event.getBlock());
						Queue.set(player, Queue.ROOM_CREATE_SECOND);
						break;

					case Queue.ROOM_CREATE_SECOND:
						land = Queue.getLand(player);
						if(!land.isInside(event.getBlock())){
							Message.normal(player, "땅 안쪽을 터치해주세요.");
							break;
						}
						Room room = land.createRoom(Queue.getPosition(player), event.getBlock());
						Queue.setPosition(player, null);
						Queue.setTemporaryRoom(player, room);
						Message.normal(player, "두번째 지점을 선택하였습니다.");
						
						ArrayList<Room> overlapRoomList = land.getOverlap(room);
						if(overlapRoomList.size() > 0){
							sb = new StringBuilder();
							f = true;
							for(Room overlap : overlapRoomList){
								if(f){
									f = false;
								}else{
									sb.append(", ");
								}
								sb.append(Integer.toString(overlap.getNumber()) + "번");
							}
							Message.alert(player, sb.toString()+ " 방과 겹칩니다. 방 생성을 취소합니다.");
							Queue.clean(player);
							break;
						}
						Message.normal(player, "방 크기 : " + room.xLength() + "x" + room.zLength() + ", 높이 : " + room.yLength() + " (" + room.size() + "블럭)");
						if(
							world.getMinRoomLength() > room.xLength() ||
							world.getMinRoomLength() > room.zLength()
						){
							Message.alert(player, "방의 한변 길이는 최소 " + Integer.toString(world.getMinRoomLength()) + "블럭 이어야 합니다. 방 생성을 취소합니다.");
							Queue.clean(player);
							break;
						}
						if(
							world.getMaxRoomLength() < room.xLength() ||
							world.getMaxRoomLength() < room.zLength()
						){
							Message.alert(player, "방의 한변 길이는 " + Integer.toString(world.getMaxRoomLength()) + "블럭을 넘을 수 없습니다. 방 생성을 취소합니다.");
							Queue.clean(player);
							break;
						}
						if(!player.isOp()){
							price = world.getRoomCreatePrice();
							Message.normal(player, "방 생성 비용은 " + Double.toString(price) + "원 입니다.");
							if(myMoney < price) {
								Message.alert(player, "돈이 부족합니다. 현재 소지한 돈 : " + Double.toString(myMoney) + "원");
								Queue.clean(player);
								break;
							}
						}
						Message.normal(player, "방을 생성하려면 /방 생성 명령어를 다시 입력해주세요. 취소하려면 /방 취소 명령어를 입력해주세요.");
						Queue.set(player, Queue.ROOM_CREATE_THIRD);
						break;

					default:
						break queueProcess;
				}
				event.setCancelled();
			}//QueueProcess
		}
	}


}